package com.qm.base.payment.controller;

import com.qm.base.payment.enums.PaymentProviderType;
import com.qm.base.payment.schema.PayNotifySchema;
import com.qm.base.payment.service.PaymentNotifyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 统一支付回调控制器。
 * <p>
 * 负责接收支付渠道异步通知、提取请求参数并委托支付回调服务处理。
 */
@RestController
@RequestMapping("/payment/callback")
public class PaymentNotifyController {

    private final PaymentNotifyService paymentNotifyService;

    public PaymentNotifyController(PaymentNotifyService paymentNotifyService) {
        this.paymentNotifyService = paymentNotifyService;
    }

    /**
     * 接收指定支付渠道的异步通知。
     *
     * @param providerCode 支付渠道编码
     * @param body         回调原始请求体
     * @param parameters   回调参数集合
     * @param request      HTTP 请求对象
     * @return 渠道要求的响应内容
     */
    @RequestMapping(path = "/{provider}", method = {RequestMethod.POST, RequestMethod.GET})
    public String notify(@PathVariable("provider") String providerCode,
                         @RequestBody(required = false) String body,
                         @RequestParam(required = false) Map<String, String> parameters,
                         HttpServletRequest request) {
        PaymentProviderType provider = PaymentProviderType.fromCode(providerCode);
        return paymentNotifyService.handleNotify(provider, buildInput(body, parameters, request));
    }

    /**
     * 构建统一支付回调输入模型。
     *
     * @param body       回调原始请求体
     * @param parameters 回调参数集合
     * @param request    HTTP 请求对象
     * @return 统一支付回调输入模型
     */
    private PayNotifySchema.Input buildInput(String body,
                                             Map<String, String> parameters,
                                             HttpServletRequest request) {
        PayNotifySchema.Input input = new PayNotifySchema.Input();
        input.setHeaders(extractHeaders(request));
        input.setParameters(parameters == null ? Map.of() : parameters);
        input.setBody(body);
        return input;
    }

    /**
     * 提取回调请求头信息。
     *
     * @param request HTTP 请求对象
     * @return 请求头集合
     */
    private Map<String, List<String>> extractHeaders(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(
                        headerName -> headerName,
                        headerName -> Collections.list(request.getHeaders(headerName))
                ));
    }

}
