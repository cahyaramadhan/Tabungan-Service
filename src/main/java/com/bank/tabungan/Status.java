package com.bank.tabungan;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class Status {
    private int value;
    private int message;

    public Integer getStatusSaldoKurang() {
        return 431;
    }

    public Integer getStatusRekeningNull() {
        return 432;
    }

    public String getMessageSaldoKurang(){
        return "Saldo Kurang";
    }

    public String getMessageRekeningNull(Integer nomorRekening) {
        return "Tidak terdapat tabungan dengan nomor nasabah: " + nomorRekening;
    }

    public int getStatusSuccess() {
        return 230;
    }

    public String getMessageSuccess() {
        return "Berhasil mengurangi saldo";
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
