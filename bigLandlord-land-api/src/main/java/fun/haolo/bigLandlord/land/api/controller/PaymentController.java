package fun.haolo.bigLandlord.land.api.controller;

import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.db.param.PaymentParam;
import fun.haolo.bigLandlord.db.vo.PaymentVO;
import fun.haolo.bigLandlord.land.api.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haolo
 * @since 2022-11-22 14:52
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/msg")
    public ResponseResult<PaymentVO> getMsg(@RequestBody PaymentParam param){
        PaymentVO paymentVO = paymentService.getPaymentMsg(param.getPhone(), param.getCode());
        return ResponseResult.success(paymentVO);
    }
}
