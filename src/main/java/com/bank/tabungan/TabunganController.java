package com.bank.tabungan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "tabungan/")
public class TabunganController {
    private final TabunganService tabunganService;

    @Autowired
    private TabunganForward tabunganForward;

    @Autowired
    private Status status;

    @Autowired
    public TabunganController(TabunganService tabunganService) {
        this.tabunganService = tabunganService;
    }

    @GetMapping
    public List<Tabungan> getTabungan() {
        return tabunganService.getTabungan();
    }

    @GetMapping(path = "{nomor_rekening}")
    public Object getTabunganById(@PathVariable("nomor_rekening") Integer nomorRekening) {
        return tabunganService.getTabunganById(nomorRekening);
    }

    @PostMapping
    public void addTabungan(@RequestBody Tabungan tabungan) {
        tabunganService.addTabungan(tabungan);
    }

    @DeleteMapping(path = "{nomor_rekening}")
    public void deleteTabungan(@PathVariable("nomor_rekening") Integer nomorRekening) {
        tabunganService.deleteTabungan(nomorRekening);
    }

    @PutMapping(path = "{nomor_rekening}")
    public void updateTabungan(
            @PathVariable("nomor_rekening") Integer nomorRekening,
            @RequestParam(required = false) Long jumlah,
            @RequestParam(required = false) String jenis) {
        tabunganService.updateTabungan(nomorRekening, jumlah, jenis);
    }

    @PutMapping(path = "kurangi_saldo")
    @ResponseBody
    public HashMap<String, Object> kurangiSaldo(@RequestBody DataInput dataInput) {
        return tabunganService.kurangiSaldo(dataInput.getNomorRekening(), dataInput.getJumlah());
    }

    @PutMapping(path = "tambah_saldo")
    @ResponseBody
    public HashMap<String, Object> tambahSaldo(@RequestBody DataInput dataInput) {
        return tabunganService.tambahSaldo(dataInput.getNomorRekening(), dataInput.getJumlah());
    }

    @PutMapping(path = "tarik_uang")
    @ResponseBody
    public HashMap<String, Object> tarikUang(@RequestBody DataInput dataInput) {
        HashMap<String, Object> response = tabunganService.tarikUang(dataInput.getNomorRekening(), dataInput.getJumlah());
        Integer statusTransaksi = ((response.get("status").equals(status.getStatusSuccess())) ? 1 : 2);
        tabunganForward.simpanTransaksi(dataInput.getNomorRekening(), 8, statusTransaksi, "[Tb] Mengambil uang sebesar " + dataInput.getJumlah());
        return response;
    }

    @PutMapping(path = "tabung")
    @ResponseBody
    public HashMap<String, Object> tabung(@RequestBody DataInput dataInput) {
        HashMap<String, Object> response = tabunganService.tabung(dataInput.getNomorRekening(), dataInput.getJumlah());
        Integer statusTransaksi = (response.get("status").equals(status.getStatusSuccess()) ? 1 : 2);
        tabunganForward.simpanTransaksi(dataInput.getNomorRekening(), 1, statusTransaksi, "[Tb] Menabung sebesar " + dataInput.getJumlah());
        return response;
    }

    @PostMapping(path = "transfer")
    @ResponseBody
    public HashMap<String, Object> transfer(@RequestBody DataInput dataInput)
    {
        HashMap<String, Object> response = new HashMap<>();
        Integer statusTransaksi;

        Integer resultPengirim = tabunganForward.validasiNasabah(dataInput.getNomorRekeningPengirim());
        if(!resultPengirim.equals(dataInput.getNomorRekeningPengirim())) {
            return tabunganService.transferFailed(response, "pengirim", resultPengirim);
        }
        Integer resultPenerima = tabunganForward.validasiNasabah(dataInput.getNomorRekeningPenerima());
        if(!resultPenerima.equals(dataInput.getNomorRekeningPenerima())) {
            return tabunganService.transferFailed(response, "penerima", resultPenerima);
        }
        response = tabunganService.transfer(dataInput.getNomorRekeningPengirim(), dataInput.getNomorRekeningPenerima(), dataInput.getJumlah());
        statusTransaksi = ((response.get("status").equals(status.getStatusSuccess())) ? 1 : 2);
        tabunganForward.simpanTransaksi(dataInput.getNomorRekeningPengirim(),
                3, statusTransaksi,
                "[Tb] Mengirim uang ke " + dataInput.getNomorRekeningPenerima()
                        + " sebesar " + dataInput.getJumlah());
        tabunganForward.simpanTransaksi(dataInput.getNomorRekeningPenerima(),
                2, statusTransaksi,
                "[Tb] Menerima uang dari " + dataInput.getNomorRekeningPengirim()
                        + " sebesar " + dataInput.getJumlah());
        return response;
    }

}
