package uy.com.hachebackend.settle.application.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.domain.model.BillDomain;
import uy.com.hachebackend.settle.domain.model.MeetDomain;
import uy.com.hachebackend.settle.domain.repository.ISettlePersist;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SettleService {

    //Meets
    public Mono<List<MeetDomain>> selectAllMeetSettle(final String idUser, Boolean active, final ISettlePersist settlePesist) {
        return settlePesist.selectAllMeetSettle(idUser, active).collectList();
    }

    public Mono<MeetDomain> selectMeetSettle(final String idMeet, final ISettlePersist settlePesist) {
        return settlePesist.selectMeetSettle(idMeet)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new Exception("No se encontró encuentro"))))
                //Con next() me quedo con el primer elemento que encuentre
                .next();
    }

    public Mono<MeetDomain> addMeetSettle(final MeetDomain meetDomain, final ISettlePersist settlePesist) {
        return settlePesist.selectMeetByIdMeetAndIdUserSettle(meetDomain.getIdMeet(), meetDomain.getIdUser())
                .flatMap(m -> Mono.error(new Exception("Meet ya existe.")))
                .switchIfEmpty(Mono.defer(() -> {

                    meetDomain.setId(null);
                    meetDomain.setActive(true);
                    meetDomain.setCreated(new Date());
                    meetDomain.setUpdated(new Date());

                    return settlePesist.saveMeetSettle(meetDomain);
                }))
                .map(meet -> (MeetDomain) meet);
    }

    public Mono<MeetDomain> updateMeetSettle(final MeetDomain meetDomain, final ISettlePersist settlePesist) {
        return settlePesist.selectMeetByIdMeetAndIdUserSettle(meetDomain.getIdMeet(), meetDomain.getIdUser())
                .switchIfEmpty(Mono.defer(() -> {
                    log.debug("Meet no encontrado: {} - {}", meetDomain.getIdMeet(), meetDomain.getIdUser());
                    return Mono.error(new Exception("Meet no encontrado."));
                }))
                .flatMap(meet -> {

                    meet.setName(meetDomain.getName());
                    meet.setUpdated(new Date());

                    return settlePesist.updateMeetSettle(meet);
                });
    }

    public Mono<MeetDomain> closeMeetSettle(final String idMeet, final String idUser, final ISettlePersist settlePesist) {
        return settlePesist.selectMeetByIdMeetAndIdUserSettle(idMeet, idUser)
                .switchIfEmpty(Mono.defer(() -> {
                    log.debug("Meet no encontrado: {} - {}", idMeet, idUser);
                    return Mono.error(new Exception("Meet no encontrado."));
                }))
                .flatMap(meet -> {

                    meet.setActive(false);
                    meet.setUpdated(new Date());

                    return settlePesist.updateMeetSettle(meet);
                });
    }

    @Transactional
    public Mono<String> removeMeetSettle(final String idUser, final String idMeet, final ISettlePersist settlePesist) {
        return settlePesist.selectMeetByIdMeetAndIdUserSettle(idMeet, idUser)
                .switchIfEmpty(Mono.defer(() -> {
                    log.debug("Meet no encontrado: {} - {}", idMeet, idUser);
                    return Mono.error(new Exception("Meet no encontrado."));
                }))
                .flatMap(meet -> {
                    //Cuantos usuarios comparten el mismo encuentro
                    return settlePesist.selectMeetSettle(idMeet)
                            .collectList()
                            .flatMap(listMeets -> {
                                if (listMeets.size() == 1) {
                                    //Si el meet no esta compartido elimino tambien los pagos asociados al Meet
                                    //Nota: Si no hago un subscribe(), NUNCA se va a ejecutar.
                                    settlePesist.removeBillsByIdMeetSettle(idMeet).subscribe();
                                }
                                return settlePesist.removeMeetSettle(idMeet, idUser);
                            });
                })
                .map(m -> "ok");
    }

    //Bills
    public Mono<BillDomain> addBillSettle(final BillDomain billDomain, final ISettlePersist settlePesist) {
        return settlePesist.selectBillByIdBillAndIdMeetSettle(billDomain.getIdBill(), billDomain.getIdMeet())
                .flatMap(m -> Mono.error(new Exception("Bill ya existe.")))
                .switchIfEmpty(Mono.defer(() -> {
                    billDomain.setCreated(new Date());
                    billDomain.setUpdated(new Date());
                    billDomain.setOwner(Objects.nonNull(billDomain.getOwner()) ?
                            billDomain.getOwner() :
                            billDomain.getListUsersPaid().get(0).getUserPaid().getUser());
                    return settlePesist.saveBillSettle(billDomain);
                }))
                .map(bill -> (BillDomain) bill);
    }

    public Mono<BillDomain> updateBillSettle(final BillDomain billDomain, final ISettlePersist settlePesist) {
        return settlePesist.selectBillByIdBillAndIdMeetSettle(billDomain.getIdBill(), billDomain.getIdMeet())
                .switchIfEmpty(Mono.defer(() -> {
                    log.info(">>>> Bill no encontrada: {}", billDomain.getIdBill());
                    return Mono.error(new Exception("Bill no existe."));
                }))
                .flatMap(b -> {
                    billDomain.setReference(billDomain.getReference());
                    billDomain.setReceipt(billDomain.getReceipt());
                    billDomain.setListUsersPaid(billDomain.getListUsersPaid());
                    billDomain.setUpdated(new Date());
                    billDomain.setOwner(billDomain.getOwner());

                    return settlePesist.saveBillSettle(billDomain);
                });
    }

    public Mono<String> updateAllBillsSettle(final List<BillDomain> listBills, final ISettlePersist settlePesist) {
        //Nota: No olvidarse del subscribe() sino no ejecuta,siempre debe tener una funcion terminal.
        listBills.stream().forEach(b -> this.updateBillSettle(b, settlePesist).subscribe());
        return Mono.just("ok");
    }

    public Mono<String> removeBillSettle(final String idBill, String idMeet, final ISettlePersist settlePesist) {
        return settlePesist.selectBillByIdBillAndIdMeetSettle(idBill, idMeet)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new Exception("Bill no existe."))))
                .flatMap(bill -> settlePesist.removeBillSettle(bill.getIdBill()))
                .map(m -> "ok");
    }

    public Mono<List<BillDomain>> selectAllBillByIdMeetSettle(final String idMeet, final ISettlePersist settlePesist) {
        return settlePesist.selectBillByIdMeetSettle(idMeet).collectList();
    }

}
