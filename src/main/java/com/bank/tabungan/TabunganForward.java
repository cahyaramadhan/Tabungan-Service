package com.bank.tabungan;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;

@Component
@NoArgsConstructor
public class TabunganForward {

    private final String nasabahService = "http://localhost:7001";
    private final String transaksiService = "http://localhost:7007";

    public void simpanTransaksi(Integer nomorRekening,
                                Integer jenisTransaksi,
                                Integer statusTransaksi,
                                String logTransaksi
                                )
    {
        HashMap<String, Object> transaksi = new HashMap<>();
        transaksi.put("nomorNasabah", nomorRekening);
        transaksi.put("jenisTransaksi", jenisTransaksi);
        transaksi.put("waktuTransaksi", LocalDateTime.now());
        transaksi.put("statusTransaksi", statusTransaksi);
        transaksi.put("logTransaksi", logTransaksi);

        WebClient client = WebClient.create(transaksiService);
        ResponseSpec responseSpec = client
                .post()
                .uri("/api/transaksi")
                .body(Mono.just(transaksi), HashMap.class)
                .retrieve();
        responseSpec.bodyToMono(HashMap.class).block();
    }

    public Integer validasiNasabah(Integer nomorRekening)
    {
        WebClient client = WebClient.create(nasabahService);
        ResponseSpec responseSpec = client
                .get()
                .uri("/nasabah/validasiNomorRekening/" + nomorRekening)
                .retrieve();
        HashMap<String, Object> nasabah = responseSpec.bodyToMono(HashMap.class).block();
        if(nasabah.get("status") != null){
            return (Integer) nasabah.get("status");
        }
        return (Integer) nasabah.get("nomorRekening");
    }

}
