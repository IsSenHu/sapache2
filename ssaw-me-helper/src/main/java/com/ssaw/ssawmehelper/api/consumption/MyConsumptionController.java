package com.ssaw.ssawmehelper.api.consumption;

import com.ssaw.commons.annotations.RequestLog;
import com.ssaw.commons.constant.Constants;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.annotations.Menu;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityApi;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityMethod;
import com.ssaw.ssawauthenticatecenterfeign.store.UserContextHolder;
import com.ssaw.ssawmehelper.api.BaseController;
import com.ssaw.ssawmehelper.dao.po.consumption.MyConsumptionPO;
import com.ssaw.ssawmehelper.model.vo.consumption.MyConsumptionQueryVO;
import com.ssaw.ssawmehelper.model.vo.consumption.MyConsumptionStatisticsVO;
import com.ssaw.ssawmehelper.model.vo.consumption.MyConsumptionVO;
import com.ssaw.ssawmehelper.service.consumption.MyConsumptionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author HuSen
 * @date 2019/3/16 13:24
 */
@Slf4j
@RestController
@RequestMapping("/api/consumption")
@SecurityApi(index = "1", group = "生活", menu = @Menu(index = "1-1", title = "我的收入", scope = "我的收入", to = "/my/helper/myConsumption"))
public class MyConsumptionController extends BaseController {

    private final MyConsumptionService myConsumptionService;

    @Autowired
    public MyConsumptionController(ApplicationContext context, MyConsumptionService myConsumptionService) {
        super(context);
        this.myConsumptionService = myConsumptionService;
    }

    /**
     * 导入消费数据
     * @param file 导入的文件
     * @return 导入结果
     * @throws IOException IO异常
     */
    @PostMapping("/importData")
    @SecurityMethod(antMatcher = "/api/consumption/importData", scope = "导入收入")
    public CommonResult importData(MultipartFile file) throws IOException {
        if (file == null) {
            return CommonResult.createResult(Constants.ResultCodes.PARAM_ERROR, "文件不能为空", null);
        }
        String filename = file.getOriginalFilename();
        log.info("上传的文件为:{}", filename);
        final String xls = ".xls";
        final String xlsx = ".xlsx";
        if (!StringUtils.endsWithAny(filename, xls, xlsx)) {
            return CommonResult.createResult(Constants.ResultCodes.PARAM_ERROR, "文件格式不正确", null);
        }
        InputStream inputStream = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        // 得到一个工作表
        XSSFSheet sheet = workbook.getSheetAt(0);
        // 获得表头
        XSSFRow rowHead = sheet.getRow(0);
        // 判断表头的数量是否正确
        final int templateCells = 4;
        if (rowHead.getPhysicalNumberOfCells() != templateCells) {
            return CommonResult.createResult(Constants.ResultCodes.PARAM_ERROR, "表头的数量不对", null);
        }
        // 获得数据的总行数
        int lastRowNum = sheet.getLastRowNum();
        List<MyConsumptionPO> myConsumptionPOS = new ArrayList<>();
        // 获得所有数据
        for (int i = 1; i <= lastRowNum - 1; i++) {
            // 获得第i行对象
            Row row = sheet.getRow(i);
            // 获得第i行第1列日期
            Date date = row.getCell(0).getDateCellValue();
            // 获得第i行第2列支出
            Cell cellOne = row.getCell(1);
            double expenditure = Objects.isNull(cellOne) ? 0 : cellOne.getNumericCellValue();
            // 获得第i行第3列收入
            Cell cellTwo = row.getCell(2);
            double income = Objects.isNull(cellTwo) ? 0 : cellTwo.getNumericCellValue();
            // 获得第i行第4列净支出
            Cell cellThree = row.getCell(3);
            double netExpenditure = Objects.isNull(cellThree) ? 0 : cellThree.getNumericCellValue();

            MyConsumptionPO po = new MyConsumptionPO();
            po.setCostDate(dateToLocalDate(date));
            po.setExpenditure(BigDecimal.valueOf(expenditure));
            po.setIncome(BigDecimal.valueOf(income));
            po.setNetExpenditure(BigDecimal.valueOf(netExpenditure));
            po.setUserId(UserContextHolder.currentUser().getId());
            myConsumptionPOS.add(po);
        }
        return myConsumptionService.saveAll(myConsumptionPOS);
    }

    /**
     * 分页查询我的消费
     * @param pageReqVO 分页查询数据模型
     * @return 分页结果
     */
    @RequestLog(desc = "分页查询我的消费")
    @PostMapping("/page")
    @SecurityMethod(antMatcher = "/api/consumption/page", scope = "读取收入信息")
    public TableData<MyConsumptionVO> page(@RequestBody PageReqVO<MyConsumptionQueryVO> pageReqVO) {
        return myConsumptionService.page(pageReqVO);
    }

    /**
     * 获取我的消费折线图所需数据
     * @param start 开始时间
     * @param end 结束时间
     * @return 折线图所需数据
     */
    @RequestLog(desc = "获取我的消费折线图所需数据")
    @GetMapping("/getMyConsumptionLineData/{start}/{end}")
    @SecurityMethod(antMatcher = "/api/consumption/getMyConsumptionLineData/*/*", scope = "收入统计")
    public CommonResult<List<MyConsumptionStatisticsVO>> getMyConsumptionLineData(@PathVariable(name = "start") String start, @PathVariable(name = "end") String end) {
        return myConsumptionService.getMyConsumptionLineData(start, end);
    }

    /**
     * Date 转 LocalDate
     * @param date Date
     * @return LocalDate
     */
    private LocalDate dateToLocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }
}