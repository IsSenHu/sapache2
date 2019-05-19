package com.ssaw.ssawauthenticatecenterservice.controller.client;

import com.ssaw.commons.annotations.RequestLog;
import com.ssaw.commons.annotations.Validating;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.annotations.Menu;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityApi;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityMethod;
import com.ssaw.ssawauthenticatecenterfeign.vo.client.ClientDetailsInfoVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.client.ClientVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.client.CreateClientVO;
import com.ssaw.ssawauthenticatecenterfeign.vo.client.QueryClientVO;
import com.ssaw.ssawauthenticatecenterservice.controller.BaseController;
import com.ssaw.ssawauthenticatecenterservice.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuSen.
 * @date 2018/12/11 11:56.
 */
@Slf4j
@RestController
@RequestMapping("/api/client")
@SecurityApi(index = "1", group = "授权管理", menu = @Menu(index = "1-3", title = "客户端", scope = "客户端管理", to = "/authenticate/center/client"))
public class ClientController extends BaseController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ApplicationContext context, ClientService clientService) {
        super(context);
        this.clientService = clientService;
    }

    /**
     * 根据clientId查询客户端
     * @param clientId clientId
     * @return 客户端
     */
    @GetMapping("/get/{clientId}")
    @RequestLog(desc = "根据clientId查询客户端")
    @SecurityMethod(antMatcher = "/api/client/get/*", scope = "读取客户端信息")
    public CommonResult<ClientDetailsInfoVO> findById(@PathVariable(name = "clientId") String clientId) {
        return clientService.findById(clientId);
    }

    /**
     * 新增客户端
     * @param createClientVO 新增客户端请求对象
     * @return 新增结果
     */
    @Validating
    @PostMapping("/add")
    @RequestLog(desc = "新增客户端")
    @SecurityMethod(antMatcher = "/api/client/add", scope = "创建客户端", button = "CLIENT_CREATE", buttonName = "添加")
    public CommonResult<CreateClientVO> add(@RequestBody CreateClientVO createClientVO) {
        return clientService.save(createClientVO);
    }

    /**
     * 分页查询客户端
     * @param pageReq 分页请求参数
     * @return 查询结果
     */
    @PostMapping("/page")
    @RequestLog(desc = "分页查询客户端")
    @SecurityMethod(antMatcher = "/api/client/page", scope = "读取客户端信息", button = "CLIENT_READ", buttonName = "搜索")
    public TableData<ClientVO> page(PageReqVO<QueryClientVO> pageReq) {
        return clientService.page(pageReq);
    }

    /**
     * 根据Id删除客户端
     * @param id ID
     * @return 删除结果
     */
    @PostMapping("/delete/{id}")
    @RequestLog(desc = "根据Id删除客户端")
    @SecurityMethod(antMatcher = "/api/client/delete/*", scope = "删除客户端", button = "CLIENT_DELETE", buttonName = "删除")
    public CommonResult<String> delete(@PathVariable(name = "id") String id) {
        return clientService.delete(id);
    }
}
