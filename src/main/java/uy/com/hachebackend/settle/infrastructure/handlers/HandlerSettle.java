package uy.com.hachebackend.settle.infrastructure.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.application.mapper.mongo.BillMapper;
import uy.com.hachebackend.settle.application.mapper.mongo.MeetMapper;
import uy.com.hachebackend.settle.application.services.SettleService;
import uy.com.hachebackend.settle.infrastructure.dto.BillDto;
import uy.com.hachebackend.settle.infrastructure.dto.MeetDto;
import uy.com.hachebackend.settle.infrastructure.mongo.persistence.SettleRepositoryImpl;
import uy.com.hachebackend.settle.initDB.InitRunApp;

import java.util.Objects;

import static uy.com.hachebackend.settle.infrastructure.handlers.HandlerUtils.createErrorResponse;
import static uy.com.hachebackend.settle.infrastructure.handlers.HandlerUtils.createSuccessResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class HandlerSettle {

    private final SettleRepositoryImpl mongoRepository;
    private final SettleService settleService;
    private final InitRunApp initRunApp;

    public Mono<ServerResponse> initSettle(final ServerRequest request) {
        System.out.println("Init userAdmin y Currenies.");
        initRunApp.runInit();
        return createSuccessResponse("Settle inicializado.");
    }

    //Meets
    public Mono<ServerResponse> addMeetSettle(final ServerRequest request) {
        System.out.println("Add Meet");
        return request.bodyToMono(MeetDto.class)
                .flatMap(meetRequest -> {
                    String idUser = meetRequest.getIdUser();
                    String idMeet = meetRequest.getIdMeet();

                    log.info("Parametros add meet: {} - {}", idUser, idMeet);

                    return settleService.addMeetSettle(MeetMapper.INSTANCE.convertDtoToDomainMongo(meetRequest), mongoRepository)
                            .flatMap(isOk -> createSuccessResponse("Meet agregado."))
                            .switchIfEmpty(Mono.defer(() -> createErrorResponse("No hubo cambios.")))
                            .onErrorResume((error) -> {
                                log.error(">>>>> Error 1: {}", error.getMessage());
                                return createErrorResponse(error.getMessage());
                            });
                })
                .onErrorResume((error) -> {
                    log.error(">>>>> Error: {}", error.getMessage());
                    return createErrorResponse(error.getMessage());
                });
    }

    public Mono<ServerResponse> updateMeetSettle(final ServerRequest request) {
        System.out.println("Update Meet");
        return request.bodyToMono(MeetDto.class)
                .flatMap(meetRequest -> {
                    String idUser = meetRequest.getIdUser();
                    String idMeet = meetRequest.getIdMeet();

                    log.info("Parametros update meet: {} - {}", idUser, idMeet);

                    return settleService.updateMeetSettle(MeetMapper.INSTANCE.convertDtoToDomainMongo(meetRequest), mongoRepository)
                            .flatMap(idOk -> createSuccessResponse("Meet modificado."))
                            .switchIfEmpty(Mono.defer(() -> createErrorResponse("No hubo cambios.")))
                            .onErrorResume((error) -> {
                                log.error(">>>>> Error 1: {}", error.getMessage());
                                return createErrorResponse(error.getMessage());
                            });
                })
                .onErrorResume((error) -> {
                    log.error(">>>>> Error: {}", error.getMessage());
                    return createErrorResponse(error.getMessage());
                });
    }

    public Mono<ServerResponse> closeMeetSettle(final ServerRequest request) {
        System.out.println("close Meet");
        return request.bodyToMono(MeetDto.class)
                .flatMap(meetRequest -> {
                    String idUser = meetRequest.getIdUser();
                    String idMeet = meetRequest.getIdMeet();

                    log.info("Parametros close meet: {} - {}", idUser, idMeet);

                    if (Objects.nonNull(idUser) && Objects.nonNull(idMeet)) {
                        return settleService.closeMeetSettle(idUser, idMeet, mongoRepository)
                                .flatMap(isOk -> createSuccessResponse("Meet cerrado."))
                                .switchIfEmpty(Mono.defer(() -> createErrorResponse("No hubo cambios.")))
                                .onErrorResume((error) -> {
                                    log.error(">>>>> Error: {}", error.getMessage());
                                    return createErrorResponse(error.getMessage());
                                });
                    } else {
                        log.error(">>>>> Error, ids null : {} - {} ", idUser, idMeet);
                        return createErrorResponse("Usuario no encontrado");
                    }
                })
                .onErrorResume((error) -> {
                    log.error(">>>>> Error: {}", error.getMessage());
                    return createErrorResponse(error.getMessage());
                });
    }

    public Mono<ServerResponse> removeMeetSettle(final ServerRequest request) {
        System.out.println("Remove Meet");
        return request.bodyToMono(MeetDto.class)
                .flatMap(meetRequest -> {
                    String idUser = meetRequest.getIdUser();
                    String idMeet = meetRequest.getIdMeet();

                    log.info("Parametros remove meet: {} - {}", idUser, idMeet);

                    if (Objects.nonNull(idUser) && Objects.nonNull(idMeet)) {
                        return settleService.removeMeetSettle(idUser, idMeet, mongoRepository)
                                .flatMap(isOk -> createSuccessResponse("Meet eliminado."))
                                .switchIfEmpty(Mono.defer(() -> createSuccessResponse("Encuentro eliminado.")))
                                .onErrorResume((error) -> {
                                    log.error(">>>>> Error: {}", error.getMessage());
                                    return createErrorResponse(error.getMessage());
                                });
                    } else {
                        log.error(">>>>> Error, ids null : {} - {} ", idUser, idMeet);
                        return createErrorResponse("Usuario no encontrado");
                    }
                })
                .onErrorResume((error) -> {
                    log.error(">>>>> Error: {}", error.getMessage());
                    return createErrorResponse(error.getMessage());
                });
    }

    public Mono<ServerResponse> selectAllMeetSettle(final ServerRequest request, Boolean active) {
        System.out.println("All Meets");
        String idUser = request.queryParam("idUser").get();
        return settleService.selectAllMeetSettle(idUser, active, mongoRepository)
                .flatMap(meet -> createSuccessResponse(meet))
                .switchIfEmpty(Mono.defer(() -> createErrorResponse("Meets no encontrados.")))
                .onErrorResume((error) -> {
                    log.error(">>>>> Error: {}", error.getMessage());
                    return createErrorResponse(error.getMessage());
                });
    }

    public Mono<ServerResponse> selectMeetSettle(final ServerRequest request) {
        System.out.println("Select Meet");
        String idMeet = request.queryParam("idMeet").get();
        return settleService.selectMeetSettle(idMeet, mongoRepository)
                .flatMap(meet -> createSuccessResponse(meet))
                .switchIfEmpty(Mono.defer(() -> createErrorResponse("Meet no encontrado.")))
                .onErrorResume((error) -> {
                    log.error(">>>>> Error: {}", error.getMessage());
                    return createErrorResponse(error.getMessage());
                });
    }

    // Bills
    public Mono<ServerResponse> addBillSettle(final ServerRequest request) {
        System.out.println("Add Bill");
        return request.bodyToMono(BillDto.class)
                .flatMap(billRequest ->
                {
                    String owner = billRequest.getOwner().getName();
                    String idMeet = billRequest.getIdMeet();

                    log.info("Parametros add bill: {} - {}", owner, idMeet, billRequest.getReference());

                    return settleService.addBillToMeetSettle(BillMapper.INSTANCE.convertDtoToDomainMongo(billRequest), mongoRepository)
                            .flatMap(user -> createSuccessResponse("Bill agregado."))
                            .switchIfEmpty(Mono.defer(() -> createErrorResponse("No hubo cambios.")))
                            .onErrorResume((error) -> {
                                log.error(">>>>> Error: {}", error.getMessage());
                                return createErrorResponse(error.getMessage());
                            });
                })
                .onErrorResume((error) -> {
                    log.error(">>>>> Error: {}", error.getMessage());
                    return createErrorResponse(error.getMessage());
                });
    }

    public Mono<ServerResponse> updateBillSettle(final ServerRequest request) {
        System.out.println("Update Bill");
        return request.bodyToMono(BillDto.class)
                .flatMap(billRequest -> {
                    String idMeet = billRequest.getIdMeet();

                    log.info("Parametros add bill: {} - {}", idMeet, billRequest.getReference());

                    return settleService.updateBillSettle(BillMapper.INSTANCE.convertDtoToDomainMongo(billRequest), mongoRepository)
                            .flatMap(user -> createSuccessResponse("Bill modificado."))
                            .switchIfEmpty(Mono.defer(() -> createErrorResponse("No hubo cambios.")))
                            .onErrorResume((error) -> {
                                log.error(">>>>> Error 1: {}", error.getMessage());
                                return createErrorResponse(error.getMessage());
                            });
                })
                .onErrorResume((error) -> {
                    log.error(">>>>> Error 2: {}", error.getMessage());
                    return createErrorResponse(error.getMessage());
                });
    }

    public Mono<ServerResponse> removeBillSettle(final ServerRequest request) {
        System.out.println("Remove Bill");
        return request.bodyToMono(BillDto.class)
                .flatMap(billRequest -> {
                            String idMeet = billRequest.getIdMeet();
                            String idBill = billRequest.getIdBill();

                            log.info("Parametros add bill: {} - {}", idMeet, billRequest.getReference());

                            return settleService.removeBillSettle(idBill, mongoRepository)
                                    .flatMap(user -> createSuccessResponse("Bill eliminado."))
                                    .switchIfEmpty(Mono.defer(() -> createSuccessResponse("Pago eliminado.")))
                                    .onErrorResume((error) -> {
                                        log.error(">>>>> Error 1: {}", error.getMessage());
                                        return createErrorResponse(error.getMessage());
                                    });
                        }
                )
                .onErrorResume((error) -> {
                    log.error(">>>>> Error: {}", error.getMessage());
                    return createErrorResponse(error.getMessage());
                });
    }

    public Mono<ServerResponse> selectAllBillSettle(final ServerRequest request) {
        System.out.println("All Bills");
        String idMeet = request.queryParam("idMeet").get();
        return settleService.selectAllBillSettle(idMeet, mongoRepository)
                .flatMap(meet -> createSuccessResponse(meet))
                .switchIfEmpty(Mono.defer(() -> createErrorResponse("Bills no encontrados.")))
                .onErrorResume((error) -> {
                    log.error(">>>>> Error: {}", error.getMessage());
                    return createErrorResponse(error.getMessage());
                });
    }

}
