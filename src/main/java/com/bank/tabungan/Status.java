package com.bank.tabungan;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class Status {

    public Integer getStatusSaldoKurang() {
        return 431;
    }

    public Integer getStatusRekeningNull() {
        return 432;
    }

    public int getStatusSuccess() { return 230; }

    public int getStatusAkunDiblokir() { return 412; }

    public int getStatusAkunTidakTerdaftar() { return 411; }

    public String getMessageSaldoKurang() {
        return "Saldo Kurang";
    }

    public String getMessageRekeningNull(Integer nomorRekening) {
        return "Tidak terdapat tabungan dengan nomor rekening: " + nomorRekening;
    }

    public String getMessageSuccessTarik(Long jumlah) {
        return "Berhasil mengambil uang sebesar " + jumlah;
    }

    public String getMessageSuccessTransfer(Integer pengirim, Integer penerima, Long jumlah) {
        return "Berhasil transfer dari " + pengirim + " ke " + penerima + " sebesar " + jumlah;
    }

    public String getMessageSuccessTabung(Long jumlah) {
        return "Berhasil menabung sebesar " + jumlah;
    }

}
