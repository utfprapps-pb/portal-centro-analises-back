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
        return processaDados(dados, "Solicitações");
    }

    public GraficoDTO getGraficoEquipamentoSolicitacao(){
        List<Tuple> dados = solicitationRepository.findGraficoEquipamentoSolicitacaoNative();
        return processaDados(dados, "Equipamentos em solicitações");
    }

    public GraficoDTO getGraficoUsuarioSituacao(){
        List<Tuple> dados = userRepository.findGraficoUsuarioSituacaoNative();
        return processaDados(dados, "Situações de usuários");
    }

    public GraficoDTO getGraficoUsuarioRole(){
        List<Tuple> dados = userRepository.findGraficoUsuarioTipoNative();
        return processaDados(dados, "Tipos de usuários");
    }

    public GraficoDTO getGraficoEquipamentoSituacao(){
        List<Tuple> dados = equipmentRepository.findGraficoEquipamentoSituacaoNative();
        return processaDados(dados, "Situações de equipamentos");
    }

    public GraficoDTO processaDados(List<Tuple> dados, String titulo){
        List<GraficoDadoDTO> lista = new ArrayList<>();
        GraficoDadoDTO dadoDTO = new GraficoDadoDTO();

        List<Long> data = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for (Tuple tuple : dados){
            labels.add(tuple.get(1,String.class));
            data.add(tuple.get(2,Long.class));
        }
        dadoDTO.setData(data);
        lista.add(dadoDTO);

        GraficoDTO graficoDTO = new GraficoDTO(
                titulo,
                labels,
                lista
        );

        return  graficoDTO;
    }

}
