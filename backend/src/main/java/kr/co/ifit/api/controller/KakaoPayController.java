package kr.co.ifit.api.controller;

import kr.co.ifit.api.request.KakaoPayApproveDtoReq;
import kr.co.ifit.api.request.KakaoPayReadyDtoReq;
import kr.co.ifit.api.response.KakaoPayApproveDtoRes;
import kr.co.ifit.api.response.KakaoPayReadyDtoRes;
import kr.co.ifit.api.service.KakaoPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/payment/kakao")
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;

    @Autowired
    public KakaoPayController(KakaoPayService kakaoPayService) {
        this.kakaoPayService = kakaoPayService;
    }

    @PostMapping("/ready")
    public ResponseEntity<?> readyToKakaoPay(@RequestBody KakaoPayReadyDtoReq request) {
        try {
            log.info("Initiating Kakao Pay payment: {}", request);
            KakaoPayReadyDtoRes response = kakaoPayService.readyToKakaoPay(request);
            log.info("Kakao Pay payment initiated successfully: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error occurred while initiating Kakao Pay payment", e);
            return ResponseEntity.badRequest().body("결제 초기화 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @GetMapping("/success")
    public ResponseEntity<?> paymentSuccess(@RequestParam("pg_token") String pgToken) {
        log.info("Payment successful. PG Token: {}", pgToken);

        return ResponseEntity.ok("결제가 성공적으로 완료되었습니다.");
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approveKakaoPay(@RequestBody KakaoPayApproveDtoReq request) {
        try {
            log.info("Approving Kakao Pay payment: {}", request);
            KakaoPayApproveDtoRes response = kakaoPayService.approveKakaoPay(request);
            log.info("Kakao Pay payment approved successfully: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error occurred while approving Kakao Pay payment", e);
            return ResponseEntity.badRequest().body("결제 승인 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> paymentCancel() {
        log.info("Payment cancelled by user");
        // 여기에 결제 취소 시 처리 로직을 추가하세요
        return ResponseEntity.ok("결제가 취소되었습니다.");
    }

    @GetMapping("/fail")
    public ResponseEntity<?> paymentFail() {
        log.error("Payment failed");
        // 여기에 결제 실패 시 처리 로직을 추가하세요
        return ResponseEntity.badRequest().body("결제에 실패했습니다.");
    }
}