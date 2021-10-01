package com.bank.tabungan;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class Status {

    public static final int CODE_SALDO_KURANG = 431;
    public static final int CODE_REKENING_NOT_FOUND = 432;


    public static final int CODE_SUCCESS = 230;

    public static final int CODE_AKUN_TIDAK_TERDAFTAR = 411;
    public static final int CODE_AKUN_DIBLOKIR = 412;

    public static final String MSG_SALDO_KURANG = "Saldo Kurang";

    public String getMessageRekeningNotFound(Integer nomorRekening) {
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
