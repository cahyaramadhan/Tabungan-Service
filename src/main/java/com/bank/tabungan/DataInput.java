package com.bank.tabungan;

import lombok.Data;

@Data
public class DataInput {
    private Integer nomorRekening;
    private Long jumlah;
    private Integer nomorRekeningPengirim;
    private Integer nomorRekeningPenerima;
}
