package br.com.thomas.foursales.application.payment;

import br.com.thomas.foursales.domain.request.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentGatewayService {

    public boolean mockPayment(PaymentRequest request) {
        log.info("Simulando pagamento para usuario de email {}", request.email());

        return Math.random() > 0.1;
    }
}
