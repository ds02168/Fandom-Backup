package com.example.NewJeans.repository;

import com.example.NewJeans.Entity.MemberShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberShipRepository  extends JpaRepository<MemberShip, Long> {
    //    MemberShip findByMem_MemID(@Param(value = "memId")Long memId);
    Optional<MemberShip> findByMem_MemIDAndIdol_IdolID(@Param(value = "memId")Long memId, @Param(value = "idolId")Long idolId);
}