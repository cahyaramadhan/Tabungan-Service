package com.bank.tabungan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TabunganRepository extends JpaRepository<Tabungan, Integer> {

}
