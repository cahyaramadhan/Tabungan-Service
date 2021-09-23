package com.bank.tabungan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data @NoArgsConstructor @AllArgsConstructor
public class Tabungan {
    @Id
    @SequenceGenerator(
            name = "nomor_rekening_sequence",
            sequenceName = "nomor_rekening_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "nomor_rekening_sequence"
    )
    private Integer nomorRekening;
    private Long saldo;
    private String jenisTabungan;

}
