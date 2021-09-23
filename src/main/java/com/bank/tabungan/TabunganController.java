package com.bank.tabungan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "tabungan/")
public class TabunganController {
    private final TabunganService tabunganService;

//    @Autowired
//    private WebClient.Builder webClientBuilder;

    @Autowired
    public TabunganController(TabunganService tabunganService) {
        this.tabunganService = tabunganService;
    }

    @GetMapping
    public List<Tabungan> getTabungan() {
        return tabunganService.getTabungan();
    }

    @PostMapping
    public void addTabungan(@RequestBody Tabungan tabungan) {
        tabunganService.addTabungan(tabungan);
    }

    @DeleteMapping(path = "{nomor_nasabah}")
    public void deleteTabungan(@PathVariable("nomor_nasabah") Integer nomorNasabah) {
        tabunganService.deleteTabungan(nomorNasabah);
    }

    @PutMapping(path = "{nomor_nasabah}")
    public void updateTabungan(
            @PathVariable("nomor_nasabah") Integer nomorNasabah,
            @RequestParam(required = false) Long jumlah,
            @RequestParam(required = false) String jenis) {
        tabunganService.updateTabungan(nomorNasabah, jumlah, jenis);
    }

    @PutMapping(path = "kurangi_saldo")
    @ResponseBody
    public HashMap<String, Object> kurangiSaldo(@RequestBody DataInput dataInput) {
        System.out.println(dataInput.getNomorRekening());
        System.out.println(dataInput.getJumlah());
        return tabunganService.kurangiSaldo(dataInput.getNomorRekening(), dataInput.getJumlah());
    }

    @PutMapping(path = "tambah_saldo")
    @ResponseBody
    public HashMap<String, Object> tambahSaldo(@RequestBody DataInput dataInput) {
        return tabunganService.tambahSaldo(dataInput.getNomorRekening(), dataInput.getJumlah());
    }

    @PostMapping(path = "transfer")
    @ResponseBody
    public HashMap<String, Object> transfer(@RequestBody DataInput dataInput) {
//        boolean statusPengirim = true;
//        boolean statusPenerima = true;

        HashMap<String, Object> data = tabunganService.transfer(dataInput.getPengirim(), dataInput.getPenerima(), dataInput.getJumlah());
        HashMap<String, Object> transaksi = new HashMap<>();

        transaksi.put("nomorNasabah", dataInput.getPengirim());
        transaksi.put("jenisTransaksi", 99);
        transaksi.put("waktuTransaksi", LocalDateTime.now());
        transaksi.put("statusTransaksi", 1);
        transaksi.put("logTransaksi", "Log Transaksi - Tabungan");

        WebClient client = WebClient.create("http://10.10.30.49:7007");
        ResponseSpec responseSpec = client.post().uri("/api/transaksi")
                                                    .body(Mono.just(transaksi), HashMap.class)
                                                    .retrieve();

        responseSpec.bodyToMono(HashMap.class).block();
        return data;
    }

    @PutMapping(path = "tarik_uang")
    @ResponseBody
    public HashMap<String, Object> tarikUang(@RequestBody DataInput dataInput) {
        return tabunganService.tarikUang(dataInput.getNomorRekening(), dataInput.getJumlah());
    }

    @PutMapping(path = "tabung")
    @ResponseBody
    public HashMap<String, Object> tabung(@RequestBody DataInput dataInput) {
        return tabunganService.tabung(dataInput.getNomorRekening(), dataInput.getJumlah());
    }

//    @GetMapping(path = "test")
//    public void test() {
//        Object obj = webClientBuilder.build()
//                .get()
//                .uri("http://10.10.30.35:7006/kantor/validasiIdKantor/1")
//                .retrieve()
//                .bodyToMono(Object.class)
//                .block();
//        System.out.println(obj.toString());
//    }

}
