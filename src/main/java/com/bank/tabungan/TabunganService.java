package com.bank.tabungan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TabunganService {
    private final TabunganRepository tabunganRepository;

    @Autowired
    public TabunganService(TabunganRepository tabunganRepository) {
        this.tabunganRepository = tabunganRepository;
    }

    public List<Tabungan> getTabungan() {
        return tabunganRepository.findAll();
    }

    public void addTabungan(Tabungan tabungan) {
        tabunganRepository.save(tabungan);
    }

    public void deleteTabungan(Integer nomorNasabah) {
        boolean exists = tabunganRepository.existsById(nomorNasabah);
        if(!exists) {
            throw new IllegalStateException("Tidak terdapat tabungan dengan nomor nasabah: " + nomorNasabah);
        }
        tabunganRepository.deleteById(nomorNasabah);
    }

    @Transactional
    public void updateTabungan(Integer nomorNasabah, Integer jumlah, String jenis) {
        Tabungan tabungan = tabunganRepository.findById(nomorNasabah)
                .orElseThrow(() -> new IllegalStateException("Tidak terdapat tabungan dengan nomor nasabah: " + nomorNasabah));

        if(jumlah != null) {
            tabungan.setJumlah(jumlah);
        }

        if(jenis != null && jenis.length() > 0) {
            tabungan.setJenis(jenis);
        }
    }

    @Transactional
    public void updateJumlah(Integer nomorNasabah, String operator, Integer jumlah) {
        Tabungan tabungan = tabunganRepository.findById(nomorNasabah)
                .orElseThrow(() -> new IllegalStateException("Tidak terdapat tabungan dengan nomor nasabah: " + nomorNasabah));

        Integer hasil = 0;

        if(Objects.equals(operator, "tambah")) {
            hasil = tabungan.getJumlah() + jumlah;
            tabungan.setJumlah(hasil);
        } else if(Objects.equals(operator, "kurang")){
            hasil = tabungan.getJumlah() - jumlah;
            if(hasil < 0) {
                throw new IllegalStateException("Jumlah tidak bisa lebih besar dari jumlah tabungan");
            } else {
                tabungan.setJumlah(hasil);
            }
        } else {
            throw new IllegalStateException("Operator tidak terdaftar. Operator yang tersedia: \"tambah\" or \"kurang\"");
        }

    }
}
