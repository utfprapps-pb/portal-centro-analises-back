package com.portal.centro.API.service;

import com.portal.centro.API.dto.GraficoDTO;
import com.portal.centro.API.dto.GraficoDadoDTO;
import com.portal.centro.API.repository.EquipmentRepository;
import com.portal.centro.API.repository.SolicitationRepository;
import com.portal.centro.API.repository.UserRepository;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    private final SolicitationRepository solicitationRepository;
    private final UserRepository userRepository;
    private final EquipmentRepository equipmentRepository;

    public DashboardService(
            SolicitationRepository solicitationRepository,
            UserRepository userRepository,
            EquipmentRepository equipmentRepository
            ) {
        this.solicitationRepository = solicitationRepository;
        this.userRepository = userRepository;
        this.equipmentRepository = equipmentRepository;
    }

    public GraficoDTO getGraficoSolicitacao(){
        List<Tuple> dados = solicitationRepository.findGraficoSolicitacoesNative();
        List<GraficoDadoDTO> lista = new ArrayList<>();
        for (Tuple tuple : dados){
            lista.add(
                    new GraficoDadoDTO(
                            tuple.get(0,Long.class),
                            tuple.get(1,String.class),
                            tuple.get(2,Long.class)
                    )
            );
        }
        GraficoDTO graficoDTO = new GraficoDTO("Solicitações", lista);
        return graficoDTO;
    }

    public GraficoDTO getGraficoEquipamentoSolicitacao(){
        List<Tuple> dados = solicitationRepository.findGraficoEquipamentoSolicitacaoNative();
        List<GraficoDadoDTO> lista = new ArrayList<>();
        for (Tuple tuple : dados){
            lista.add(
                    new GraficoDadoDTO(
                            tuple.get(0,Long.class),
                            tuple.get(1,String.class),
                            tuple.get(2,Long.class)
                    )
            );
        }
        GraficoDTO graficoDTO = new GraficoDTO("Equipamentos em solicitações", lista);
        return graficoDTO;
    }

    public GraficoDTO getGraficoUsuarioSituacao(){
        List<Tuple> dados = userRepository.findGraficoUsuarioSituacaoNative();
        List<GraficoDadoDTO> lista = new ArrayList<>();
        for (Tuple tuple : dados){
            lista.add(
                    new GraficoDadoDTO(
                            tuple.get(0,Long.class),
                            tuple.get(1,String.class),
                            tuple.get(2,Long.class)
                    )
            );
        }
        GraficoDTO graficoDTO = new GraficoDTO("Situações de usuários", lista);
        return graficoDTO;
    }

    public GraficoDTO getGraficoUsuarioRole(){
        List<Tuple> dados = userRepository.findGraficoUsuarioTipoNative();
        List<GraficoDadoDTO> lista = new ArrayList<>();
        for (Tuple tuple : dados){
            lista.add(
                new GraficoDadoDTO(
                        tuple.get(0,Long.class),
                        tuple.get(1,String.class),
                        tuple.get(2,Long.class))
            );
        }

        GraficoDTO graficoDTO = new GraficoDTO("Tipos de usuários", lista);
        return graficoDTO;
    }

    public GraficoDTO getGraficoEquipamentoSituacao(){
        List<Tuple> dados = equipmentRepository.findGraficoEquipamentoSituacaoNative();

        List<GraficoDadoDTO> lista = new ArrayList<>();
        for (Tuple tuple : dados){
            lista.add(
                    new GraficoDadoDTO(
                            tuple.get(0,Long.class),
                            tuple.get(1,String.class),
                            tuple.get(2,Long.class)
                    )
            );
        }
        GraficoDTO graficoDTO = new GraficoDTO("Situações de equipamentos", lista);
        return graficoDTO;
    }

}
