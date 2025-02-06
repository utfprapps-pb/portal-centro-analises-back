package com.portal.centro.API.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestValueService {

    // TODO
//    public BigDecimal calculate(TechnicalReport report) {
//        Solicitation solicitation = report.getSolicitation();
//        Equipment equipment = report.getSolicitation().getEquipment();
//        switch (solicitation.getTypeUser()) {
//            case UTFPR -> {
//                if (equipment.getValueHourUtfpr() != null) {
//                    return equipment.getValueHourUtfpr().multiply(new BigDecimal(report.getAmountHours()));
//                } else if (equipment.getValueSampleUtfpr() != null) {
//                    return equipment.getValueSampleUtfpr().multiply(new BigDecimal(report.getAmountSamples()));
//                }
//            }
//            case PARTNER -> {
//                if (equipment.getValueHourPartner() != null) {
//                    return equipment.getValueHourPartner().multiply(new BigDecimal(report.getAmountHours()));
//                } else if (equipment.getValueSamplePartner() != null) {
//                    return equipment.getValueSamplePartner().multiply(new BigDecimal(report.getAmountSamples()));
//                }
//            }
//            case EXTERNAL -> {
//                if (equipment.getValueHourExternal() != null) {
//                    return equipment.getValueHourExternal().multiply(new BigDecimal(report.getAmountHours()));
//                } else if (equipment.getValueSampleExternal() != null) {
//                    return equipment.getValueSampleExternal().multiply(new BigDecimal(report.getAmountSamples()));
//                }
//            }
//            default -> {
//                return null;
//            }
//        }
//        return null;
//    }
}
