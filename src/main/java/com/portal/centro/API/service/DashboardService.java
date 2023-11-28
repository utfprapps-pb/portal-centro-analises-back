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

        GraficoDTO graficoDTO = new GraficoDTO();
        graficoDTO.setTitulo("Solicitações");

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

        graficoDTO.setDatasets(lista);
        graficoDTO.setLabels(labels);

        return graficoDTO;
    }

    public GraficoDTO getGraficoEquipamentoSolicitacao(){
        List<Tuple> dados = solicitationRepository.findGraficoEquipamentoSolicitacaoNative();

        GraficoDTO graficoDTO = new GraficoDTO();
        graficoDTO.setTitulo("Equipamentos em solicitações");

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

        graficoDTO.setDatasets(lista);
        graficoDTO.setLabels(labels);

        return graficoDTO;
    }

    public GraficoDTO getGraficoUsuarioSituacao(){
        List<Tuple> dados = userRepository.findGraficoUsuarioSituacaoNative();

        GraficoDTO graficoDTO = new GraficoDTO();
        graficoDTO.setTitulo("Situações de usuários");

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

        graficoDTO.setDatasets(lista);
        graficoDTO.setLabels(labels);

        return graficoDTO;
    }

    public GraficoDTO getGraficoUsuarioRole(){
        List<Tuple> dados = userRepository.findGraficoUsuarioTipoNative();

        GraficoDTO graficoDTO = new GraficoDTO();
        graficoDTO.setTitulo("Tipos de usuários");

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

        graficoDTO.setDatasets(lista);
        graficoDTO.setLabels(labels);

        return graficoDTO;
    }

    public GraficoDTO getGraficoEquipamentoSituacao(){
        List<Tuple> dados = equipmentRepository.findGraficoEquipamentoSituacaoNative();

        GraficoDTO graficoDTO = new GraficoDTO();
        graficoDTO.setTitulo("Situações de equipamentos");

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

        graficoDTO.setDatasets(lista);
        graficoDTO.setLabels(labels);

        return graficoDTO;
    }

}
