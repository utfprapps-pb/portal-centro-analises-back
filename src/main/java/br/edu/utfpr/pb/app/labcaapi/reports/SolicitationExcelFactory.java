package br.edu.utfpr.pb.app.labcaapi.reports;

import br.edu.utfpr.pb.app.labcaapi.enums.SolicitationFormType;
import br.edu.utfpr.pb.app.labcaapi.enums.SolicitationProjectNature;
import br.edu.utfpr.pb.app.labcaapi.enums.Type;
import br.edu.utfpr.pb.app.labcaapi.model.Solicitation;
import br.edu.utfpr.pb.app.labcaapi.reports.excelFactory.ExcelFactory;
import br.edu.utfpr.pb.app.labcaapi.reports.excelFactory.ExcelFactoryCell;
import br.edu.utfpr.pb.app.labcaapi.reports.excelFactory.ExcelFactoryRow;
import br.edu.utfpr.pb.app.labcaapi.reports.excelFactory.ExcelFactorySheet;
import br.edu.utfpr.pb.app.labcaapi.service.EquipmentService;
import br.edu.utfpr.pb.app.labcaapi.service.SolicitationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SolicitationExcelFactory {

    private final ExcelFactory excelFactory;
    private final SolicitationService solicitationService;

    public SolicitationExcelFactory(ExcelFactory excelFactory,
                                    EquipmentService equipmentService,
                                    SolicitationService solicitationService
    ) {
        this.excelFactory = excelFactory;
        this.solicitationService = solicitationService;
    }

    public byte[] gerarRelatorioCompleto() throws Exception {
        List<Solicitation> dados = solicitationService.getAll();
        dados.sort((a, b) -> a.getId().compareTo(b.getId()) < 0 ? -1 : 1);

        List<ExcelFactorySheet> sheets = new ArrayList<>();
        sheets.add(this.createSheetSolicitacoes(dados));
        sheets.add(this.createSheetTipoSolicitacaoXNaturezaProjeto(dados));
        sheets.add(this.createSheetTipoSolicitacaoXTipoPessoaSolicitante(dados));
        sheets.add(this.createSheetTipoSolicitacaoXNaturezaProjetoXTipoPessoaSolicitante(dados));

        return excelFactory.generateExcel(sheets);
    }

    private ExcelFactorySheet createSheetSolicitacoes(List<Solicitation> dados) {
        List<ExcelFactoryRow> rows = new ArrayList<>();

        ExcelFactoryRow header = new ExcelFactoryRow()
                .addCell(new ExcelFactoryCell("Código", true))
                .addCell(new ExcelFactoryCell("Status", true))
                .addCell(new ExcelFactoryCell("Responsável", true))
                .addCell(new ExcelFactoryCell("Grupo do Responsável", true))
                .addCell(new ExcelFactoryCell("Tipo do Responsável", true))
                .addCell(new ExcelFactoryCell("Solicitante", true))
                .addCell(new ExcelFactoryCell("Grupo do Solicitante", true))
                .addCell(new ExcelFactoryCell("Tipo do Solicitante", true))
                .addCell(new ExcelFactoryCell("Data de Criação", true))
                .addCell(new ExcelFactoryCell("Tipo do Formulário", true))
                .addCell(new ExcelFactoryCell("Natureza do Projeto", true))
                .addCell(new ExcelFactoryCell("Outra Natureza", true))
                .addCell(new ExcelFactoryCell("Observações", true));

        rows.add(header);

        for (Solicitation solicitation : dados) {
            ExcelFactoryRow row = new ExcelFactoryRow()
                    .addCell(new ExcelFactoryCell().setValue(solicitation.getId()))
                    .addCell(new ExcelFactoryCell().setValue(solicitation.getStatus().getContent()))
                    .addCell(new ExcelFactoryCell().setValue(solicitation.getResponsavel().getId() + " - " + solicitation.getResponsavel().getName()))
                    .addCell(new ExcelFactoryCell().setValue(solicitation.getResponsavel().getRole().toReport()))
                    .addCell(new ExcelFactoryCell().setValue(solicitation.getResponsavel().getType().getContent()))
                    .addCell(new ExcelFactoryCell().setValue(solicitation.getCreatedBy().getId() + " - " + solicitation.getCreatedBy().getName()))
                    .addCell(new ExcelFactoryCell().setValue(solicitation.getCreatedBy().getRole().toReport()))
                    .addCell(new ExcelFactoryCell().setValue(solicitation.getCreatedBy().getType().getContent()))
                    .addCell(new ExcelFactoryCell().setValue(solicitation.getCreatedAt()))
                    .addCell(new ExcelFactoryCell().setValue(solicitation.getSolicitationType().getContent()))
                    .addCell(new ExcelFactoryCell().setValue(solicitation.getProjectNature().getContent()));
            if (solicitation.getProjectNature().equals(SolicitationProjectNature.OTHER)) {
                row.addCell(new ExcelFactoryCell().setValue(solicitation.getOtherProjectNature()));
            } else {
                row.addCell(ReportContants.BLANK_CELL);
            }
            row.addCell(new ExcelFactoryCell().setValue(solicitation.getObservation()));

            rows.add(row);
        }

        return new ExcelFactorySheet("Solicitações", rows);
    }

    private ExcelFactorySheet createSheetTipoSolicitacaoXNaturezaProjeto(List<Solicitation> dados) {
        List<ExcelFactoryRow> rows = new ArrayList<>();

        ExcelFactoryRow header = new ExcelFactoryRow()
                .addCell(new ExcelFactoryCell("Ano", true))
                .addCell(new ExcelFactoryCell("Tipo de Solicitação", true))
                .addCell(new ExcelFactoryCell("Natureza do Projeto", true))
                .addCell(new ExcelFactoryCell("Total de Solicitações", true));

        rows.add(header);

        Map<Integer, List<Solicitation>> solicitationsPorAno = agruparPorAno(dados);
        List<Integer> anos = new ArrayList<>(solicitationsPorAno.keySet());
        anos.sort((a, b) -> a.compareTo(b) < 0 ? -1 : 1);

        for (Integer ano : anos) {
            List<Solicitation> solicitations = solicitationsPorAno.get(ano);
            List<SolicitationFormType> solicitationsTypes = solicitations.stream()
                    .map(Solicitation::getSolicitationType)
                    .distinct()
                    .toList();

            List<SolicitationProjectNature> solicitationsNatures = solicitations.stream()
                    .map(Solicitation::getProjectNature)
                    .distinct()
                    .toList();

            for (SolicitationFormType type : solicitationsTypes) {
                for (SolicitationProjectNature nature : solicitationsNatures) {
                    long total = solicitations.stream()
                            .filter(s -> s.getSolicitationType().equals(type))
                            .filter(s -> s.getProjectNature().equals(nature))
                            .count();

                    if (total == 0) {
                        continue;
                    }

                    ExcelFactoryRow row = new ExcelFactoryRow()
                            .addCell(new ExcelFactoryCell().setValue(ano))
                            .addCell(new ExcelFactoryCell().setValue(type.getContent()))
                            .addCell(new ExcelFactoryCell().setValue(nature.getContent()))
                            .addCell(new ExcelFactoryCell().setValue(total));

                    rows.add(row);
                }
            }
        }

        return new ExcelFactorySheet("Solicitacao X Natureza", rows);
    }

    private ExcelFactorySheet createSheetTipoSolicitacaoXTipoPessoaSolicitante(List<Solicitation> dados) {
        List<ExcelFactoryRow> rows = new ArrayList<>();

        ExcelFactoryRow header = new ExcelFactoryRow()
                .addCell(new ExcelFactoryCell("Ano", true))
                .addCell(new ExcelFactoryCell("Tipo de Solicitação", true))
                .addCell(new ExcelFactoryCell("Tipo do Solicitante", true))
                .addCell(new ExcelFactoryCell("Total de Solicitações", true));

        rows.add(header);

        Map<Integer, List<Solicitation>> solicitationsPorAno = agruparPorAno(dados);
        List<Integer> anos = new ArrayList<>(solicitationsPorAno.keySet());
        anos.sort((a, b) -> a.compareTo(b) < 0 ? -1 : 1);

        for (Integer ano : anos) {
            List<Solicitation> solicitations = solicitationsPorAno.get(ano);
            List<SolicitationFormType> solicitationsTypes = solicitations.stream()
                    .map(Solicitation::getSolicitationType)
                    .distinct()
                    .toList();

            List<Type> roles = solicitations.stream()
                    .map(it -> it.getCreatedBy().getRole())
                    .distinct()
                    .toList();

            for (SolicitationFormType type : solicitationsTypes) {
                for (Type role : roles) {
                    long total = solicitations.stream()
                            .filter(s -> s.getSolicitationType().equals(type))
                            .filter(s -> s.getCreatedBy().getRole().equals(role))
                            .count();

                    if (total == 0) {
                        continue;
                    }

                    ExcelFactoryRow row = new ExcelFactoryRow()
                            .addCell(new ExcelFactoryCell().setValue(ano))
                            .addCell(new ExcelFactoryCell().setValue(type.getContent()))
                            .addCell(new ExcelFactoryCell().setValue(role.toReport()))
                            .addCell(new ExcelFactoryCell().setValue(total));

                    rows.add(row);
                }
            }
        }

        return new ExcelFactorySheet("Solicitacao X Grupo do Usuario", rows);
    }

    private ExcelFactorySheet createSheetTipoSolicitacaoXNaturezaProjetoXTipoPessoaSolicitante(List<Solicitation> dados) {
        List<ExcelFactoryRow> rows = new ArrayList<>();

        ExcelFactoryRow header = new ExcelFactoryRow()
                .addCell(new ExcelFactoryCell("Ano", true))
                .addCell(new ExcelFactoryCell("Tipo de Solicitação", true))
                .addCell(new ExcelFactoryCell("Natureza do Projeto", true))
                .addCell(new ExcelFactoryCell("Tipo do Solicitante", true))
                .addCell(new ExcelFactoryCell("Total de Solicitações", true));

        rows.add(header);

        Map<Integer, List<Solicitation>> solicitationsPorAno = agruparPorAno(dados);
        List<Integer> anos = new ArrayList<>(solicitationsPorAno.keySet());
        anos.sort((a, b) -> a.compareTo(b) < 0 ? -1 : 1);

        for (Integer ano : anos) {
            List<Solicitation> solicitations = solicitationsPorAno.get(ano);
            List<SolicitationFormType> solicitationsTypes = solicitations.stream()
                    .map(Solicitation::getSolicitationType)
                    .distinct()
                    .toList();

            List<SolicitationProjectNature> solicitationsNatures = solicitations.stream()
                    .map(Solicitation::getProjectNature)
                    .distinct()
                    .toList();

            List<Type> roles = solicitations.stream()
                    .map(it -> it.getCreatedBy().getRole())
                    .distinct()
                    .toList();

            for (SolicitationFormType type : solicitationsTypes) {
                for (SolicitationProjectNature nature : solicitationsNatures) {
                    for (Type role : roles) {
                        long total = solicitations.stream()
                                .filter(s -> s.getSolicitationType().equals(type))
                                .filter(s -> s.getProjectNature().equals(nature))
                                .filter(s -> s.getCreatedBy().getRole().equals(role))
                                .count();

                        if (total == 0) {
                            continue;
                        }

                        ExcelFactoryRow row = new ExcelFactoryRow()
                                .addCell(new ExcelFactoryCell().setValue(ano))
                                .addCell(new ExcelFactoryCell().setValue(type.getContent()))
                                .addCell(new ExcelFactoryCell().setValue(nature.getContent()))
                                .addCell(new ExcelFactoryCell().setValue(role.toReport()))
                                .addCell(new ExcelFactoryCell().setValue(total));

                        rows.add(row);
                    }
                }
            }
        }

        return new ExcelFactorySheet("Solicitacao X Natureza X Grupo do Usuario", rows);
    }

    private Map<Integer, List<Solicitation>> agruparPorAno(List<Solicitation> solicitations) {
        return solicitations.stream()
                .filter(s -> s.getCreatedAt() != null)
                .collect(Collectors.groupingBy(s -> s.getCreatedAt().getYear()));
    }

}
