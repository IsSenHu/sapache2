package com.ssaw.ssawmehelper.api.collection;

import com.ssaw.commons.annotations.RequestLog;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.annotations.Menu;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityApi;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityMethod;
import com.ssaw.ssawmehelper.api.BaseController;
import com.ssaw.ssawmehelper.model.vo.collection.MyCollectionCreateRequestVO;
import com.ssaw.ssawmehelper.model.vo.collection.MyCollectionQueryVO;
import com.ssaw.ssawmehelper.model.vo.collection.MyCollectionVO;
import com.ssaw.ssawmehelper.service.collection.MyCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author HuSen
 * @date 2019/4/19 14:15
 */
@RestController
@RequestMapping("/api/collection")
@SecurityApi(index = "1", group = "生活", menu = @Menu(index = "1-2", title = "收藏", scope = "收藏", to = "/my/helper/myCollection"))
public class MyCollectionController extends BaseController {

    private final MyCollectionService myCollectionService;

    @Autowired
    public MyCollectionController(ApplicationContext context, MyCollectionService myCollectionService) {
        super(context);
        this.myCollectionService = myCollectionService;
    }

    /**
     * 新增收藏类别
     *
     * @param classification 收藏类别
     * @return 新增结果
     */
    @RequestLog(desc = "新增收藏类别")
    @GetMapping("/newClassification")
    @SecurityMethod(antMatcher = "/api/collection/newClassification*", scope = "新增收藏类别")
    public CommonResult<String> newClassification(@RequestParam("classification") String classification) {
        return myCollectionService.newClassification(classification);
    }

    /**
     * 获取所有收藏类别
     *
     * @return 所有收藏类别
     */
    @RequestLog(desc = "获取所有收藏类别")
    @GetMapping("/allClassification")
    @SecurityMethod(antMatcher = "/api/collection/allClassification", scope = "获取所有收藏类别")
    public CommonResult<Set<String>> allClassification() {
        return myCollectionService.allClassification();
    }

    /**
     * 创建我的收藏
     *
     * @param requestVO 创建我的收藏请求数据模型
     * @return 创建结果
     */
    @RequestLog(desc = "创建我的收藏")
    @PostMapping("/create")
    @SecurityMethod(antMatcher = "/api/collection/create", scope = "创建我的收藏")
    public CommonResult<MyCollectionCreateRequestVO> create(@RequestBody MyCollectionCreateRequestVO requestVO) {
        return myCollectionService.create(requestVO);
    }

    /**
     * 增加收藏分数
     *
     * @param id 收藏ID
     * @return 操作结果
     */
    @RequestLog(desc = "增加收藏分数")
    @PostMapping("/addScore/{id}")
    @SecurityMethod(antMatcher = "/api/collection/addScore/*", scope = "增加收藏分数")
    public CommonResult<Long> addScore(@PathVariable(name = "id") Long id) {
        return myCollectionService.addScore(id);
    }

    /**
     * 收藏列表
     *
     * @param pageReqVO 分页参数
     * @return 收藏列表
     */
    @RequestLog(desc = "收藏列表")
    @RequestMapping("/list")
    @SecurityMethod(antMatcher = "/api/collection/list", scope = "收藏列表")
    public TableData<MyCollectionVO> list(@RequestBody PageReqVO<MyCollectionQueryVO> pageReqVO) {
        return myCollectionService.list(pageReqVO);
    }
}