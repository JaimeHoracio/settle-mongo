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
import uy.com.hachebackend.settle.infrastructure.dto.BillRequest;
import uy.com.hachebackend.settle.infrastructure.dto.ErrorDto;
import uy.com.hachebackend.settle.infrastructure.dto.MeetRequest;
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
        initRunApp.runInit();
        return createSuccessResponse("Settle inicializado.");
    }

    public Mono<ServerResponse> selectSettle(final ServerRequest request) {
        System.out.println("Select Meet");
        return request.bodyToMono(MeetRequest.class)
                .flatMap(meetRequest -> {
                    String idMeet = meetRequest.getMeet().getIdMeet();

                    log.info("Parametros select meet: {} ", idMeet);

                    return settleService.selectMeetSettle(idMeet, mongoRepository)
                            .flatMap(meet -> createSuccessResponse(meet))
                            .switchIfEmpty(createErrorResponse("No hubo cambios."))
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

    public Mono<ServerResponse> addMeetSettle(final ServerRequest request) {
        System.out.println("Add Meet");
        return request.bodyToMono(MeetRequest.class)
                .flatMap(meetRequest -> {
                    String email = meetRequest.getEmail();
                    String idMeet = meetRequest.getMeet().getIdMeet();

                    log.info("Parametros add meet: {} - {}", email, idMeet);

                    return settleService.addMeetSettle(email, MeetMapper.INSTANCE.convertDtoToDomainMongo(meetRequest.getMeet()), mongoRepository)
                            .flatMap(user -> createSuccessResponse("Encuentro agregado."))
                            .switchIfEmpty(createErrorResponse("No hubo cambios."))
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
        return request.bodyToMono(MeetRequest.class)
                .flatMap(meetRequest -> {
                    String email = meetRequest.getEmail();
                    String idMeet = meetRequest.getMeet().getIdMeet();

                    log.info("Parametros update meet: {} - {}", email, idMeet);

                    return settleService.updateMeetSettle(email, MeetMapper.INSTANCE.convertDtoToDomainMongo(meetRequest.getMeet()), mongoRepository)
                            .flatMap(user -> createSuccessResponse("Encuentro modificado."))
                            .switchIfEmpty(createErrorResponse("No hubo cambios."))
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
        return request.bodyToMono(MeetRequest.class)
                .flatMap(meetRequest -> {
                    String email = meetRequest.getEmail();
                    String idMeet = meetRequest.getMeet().getIdMeet();

                    log.info("Parametros close meet: {} - {}", email, idMeet);

                    if (Objects.nonNull(email) && Objects.nonNull(idMeet)) {
                        return settleService.closeMeetSettle(email, idMeet, mongoRepository)
                                .flatMap(user -> ServerResponse.ok().body(Mono.just("Encuentro cerrado."), String.class))
                                .switchIfEmpty(ServerResponse.ok()
                                        .body(Mono.just(ErrorDto.builder()
                                                .message("No hubo cambios.")
                                                .codeError(0).build()), ErrorDto.class))
                                .onErrorResume((error) -> {
                                    log.error(">>>>> Error 1: {}", error.getMessage());
                                    return ServerResponse.badRequest()
                                            .body(Mono.just(ErrorDto.builder().message(error.getMessage()).codeError(0).build()),
                                                    ErrorDto.class);
                                });
                    } else {
                        return ServerResponse.badRequest()
                                .body(Mono.just(ErrorDto.builder().message("Usuario no encontrado").codeError(0).build()),
                                        ErrorDto.class);
                    }
                })
                .onErrorResume((error) -> {
                    log.error(">>>>> Error: {}", error.getMessage());
                    return ServerResponse.badRequest()
                            .body(Mono.just(ErrorDto.builder().message(error.getMessage()).codeError(0).build()),
                                    ErrorDto.class);
                });

    }

    public Mono<ServerResponse> removeMeetSettle(final ServerRequest request) {
        System.out.println("Remove Meet");
        return request.bodyToMono(MeetRequest.class)
                .flatMap(meetRequest -> {
                    String email = meetRequest.getEmail();
                    String idMeet = meetRequest.getMeet().getIdMeet();


                    log.info("Parametros remove meet: {} - {}", email, idMeet);


                    if (Objects.nonNull(email) && Objects.nonNull(idMeet)) {
                        return settleService.removeMeetSettle(email, idMeet, mongoRepository)
                                .flatMap(user -> ServerResponse.ok().body(Mono.just("Encuentro eliminado."), String.class))
                                .switchIfEmpty(ServerResponse.ok()
                                        .body(Mono.just(ErrorDto.builder()
                                                .message("No hubo cambios.")
                                                .codeError(0).build()), ErrorDto.class))
                                .onErrorResume((error) -> {
                                    log.error(">>>>> Error 1: {}", error.getMessage());
                                    return ServerResponse.badRequest()
                                            .body(Mono.just(ErrorDto.builder().message(error.getMessage()).codeError(0).build()),
                                                    ErrorDto.class);
                                });
                    } else {
                        return ServerResponse.badRequest()
                                .body(Mono.just(ErrorDto.builder().message("Usuario no encontrado").codeError(0).build()),
                                        ErrorDto.class);
                    }
                })
                .onErrorResume((error) -> {
                    log.error(">>>>> Error: {}", error.getMessage());
                    return ServerResponse.badRequest()
                            .body(Mono.just(ErrorDto.builder().message(error.getMessage()).codeError(0).build()),
                                    ErrorDto.class);
                });

    }

    public Mono<ServerResponse> addBillSettle(final ServerRequest request) {
        System.out.println("Add Bill");
        return request.bodyToMono(BillRequest.class)
                .flatMap(billRequest ->
                {
                    String email = billRequest.getEmail();
                    String idMeet = billRequest.getIdMeet();
                    BillDto bill = billRequest.getBill();

                    log.info("Parametros add bill: {} - {} - {}", email, idMeet, bill.getReference());

                    if (Objects.nonNull(email) && Objects.nonNull(idMeet)) {
                        return settleService.addBillListMeetSettle(email, idMeet, BillMapper.INSTANCE.convertDtoToDomainMongo(bill), mongoRepository)
                                .flatMap(user -> createSuccessResponse("Pago agregado."))
                                .switchIfEmpty(createErrorResponse("Pago no agregado."))
                                .onErrorResume((error) -> {
                                    log.error(">>>>> Error 1: {}", error.getMessage());
                                    return createErrorResponse(error.getMessage());
                                });
                    } else {
                        return createErrorResponse("Usuario no encontrado");
                    }
                })
                .onErrorResume((error) -> {
                    log.error(">>>>> Error: {}", error.getMessage());
                    return createErrorResponse(error.getMessage());
                });
    }

    public Mono<ServerResponse> updateBillSettle(final ServerRequest request) {
        System.out.println("Update Bill");
        return request.bodyToMono(BillRequest.class)
                .flatMap(billRequest -> {
                    String email = billRequest.getEmail();
                    String idMeet = billRequest.getIdMeet();
                    BillDto bill = billRequest.getBill();

                    log.info("Parametros add bill: {} - {} - {}", email, idMeet, bill.getReference());

                    return settleService.updateBillSettle(email, idMeet, BillMapper.INSTANCE.convertDtoToDomainMongo(bill), mongoRepository)
                            .flatMap(user -> createSuccessResponse("Pago modificado."))
                            .switchIfEmpty(createErrorResponse("No hubo cambios."))
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
        return request.bodyToMono(BillRequest.class)
                .flatMap(billRequest -> {
                            String email = billRequest.getEmail();
                            String idMeet = billRequest.getIdMeet();
                            String idBill = billRequest.getBill().getIdBill();

                            log.info("Parametros add bill: {} - {} - {}", email, idMeet, idBill);

                            if (Objects.nonNull(email) && Objects.nonNull(idMeet) && Objects.nonNull(idBill)) {
                                return settleService.removeBillSettle(email, idMeet, idBill, mongoRepository)
                                        .flatMap(user -> createSuccessResponse("Pago eliminado."))
                                        .switchIfEmpty(createErrorResponse("No hubo cambios."))
                                        .onErrorResume((error) -> {
                                            log.error(">>>>> Error 1: {}", error.getMessage());
                                            return createErrorResponse(error.getMessage());
                                        });
                            } else {
                                return createErrorResponse("Usuario no encontrado");
                            }
                        }
                )
                .onErrorResume((error) -> {
                    log.error(">>>>> Error: {}", error.getMessage());
                    return createErrorResponse(error.getMessage());
                });

    }

}
