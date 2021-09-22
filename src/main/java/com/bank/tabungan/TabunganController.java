package com.bank.tabungan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "tabungan/")
public class TabunganController {
    private final TabunganService tabunganService;

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
            @RequestParam(required = false) Integer jumlah,
            @RequestParam(required = false) String jenis) {
        tabunganService.updateTabungan(nomorNasabah, jumlah, jenis);
    }

    @PutMapping(path = "update_jumlah/{nomor_nasabah}")
    public void updateJumlah(
            @PathVariable("nomor_nasabah") Integer nomorNasabah,
            @RequestParam(required = true) String operator,
            @RequestParam(required = true) Integer jumlah) {
        tabunganService.updateJumlah(nomorNasabah, operator, jumlah);
    }
}
