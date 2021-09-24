package com.bank.tabungan;

import lombok.Data;

@Data
public class DataInput {
    private Integer nomorRekening; // digunakan jika hanya input 1 nomorRekening
    private Long jumlah;
    private Integer nomorRekeningPengirim; // khusus /transfer: nomorRekening Pengirim
    private Integer nomorRekeningPenerima; // khusus /transfer: nomorRekening Penerima
}
