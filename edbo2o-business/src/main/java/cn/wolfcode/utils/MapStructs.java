package cn.wolfcode.utils;


import cn.wolfcode.business.archives.domain.BusCustomerArchives;
import cn.wolfcode.business.archives.domain.vo.BusCustomerArchivesVO;
import cn.wolfcode.contract.domain.ContractManagement;
import cn.wolfcode.contract.domain.dto.ContractManagementReqDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Map;


/**
 * POJO对象映射
 *
 * @author YL
 * @since 2023-06-11 10:30:52
 */
@Mapper
public interface MapStructs {
    MapStructs INSTANCE = Mappers.getMapper(MapStructs.class);

    @Mappings({
            @Mapping(source = "customerName", target = "customerName"),
            @Mapping(source = "customerPhone", target = "customerPhone"),
            @Mapping(source = "gender", target = "gender"),
    })
    BusCustomerArchives dto2po(BusCustomerArchivesVO reqDTO);

    @Mappings({
            @Mapping(source = "customerName", target = "customerName"),
            @Mapping(source = "customerPhone", target = "customerPhone"),
            @Mapping(source = "gender", target = "gender"),
    })
    BusCustomerArchivesVO po2dto(BusCustomerArchives po);

    @Mappings({
            @Mapping(target = "customerName", expression = "java(String.valueOf(String.valueOf(map.get(\"customerName\"))))"),
            @Mapping(target = "customerPhone", expression = "java(String.valueOf(String.valueOf(map.get(\"customerPhone\"))))"),
            @Mapping(target = "gender", expression = "java(Integer.valueOf(String.valueOf(map.get(\"gender\"))))"),
    })
    void map2dto(@MappingTarget BusCustomerArchivesVO busCustomerArchivesVO, Map<String, Object> map);

    @Mappings({
            @Mapping(target = "customerName", expression = "java(String.valueOf(String.valueOf(map.get(\"name\"))))"),
            @Mapping(target = "customerPhone", expression = "java(String.valueOf(String.valueOf(map.get(\"phone\"))))"),
            @Mapping(target = "customerVip", expression = "java(Integer.valueOf(String.valueOf(map.get(\"customerVip\"))))"),
            @Mapping(target = "gender", expression = "java(Integer.valueOf(String.valueOf(map.get(\"gender\"))))"),
            @Mapping(target = "carSeries", expression = "java(String.valueOf(String.valueOf(map.get(\"carSeries\"))))"),
            @Mapping(target = "enterUser", expression = "java(String.valueOf(String.valueOf(map.get(\"enterUser\"))))"),
            @Mapping(target = "enterTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "isPayRecord", expression = "java(Integer.valueOf(String.valueOf(map.get(\"isPayRecord\"))))"),
    })
    void map2po(@MappingTarget BusCustomerArchives busCustomerArchives, Map<String, Object> map);



    @Mappings({
            @Mapping(source = "affiliateCustomers", target = "affiliateCustomers"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "no", target = "no"),
            @Mapping(source = "amount", target = "amount"),
            @Mapping(source = "startDate", target = "startDate"),
            @Mapping(source = "endDate", target = "endDate"),
            @Mapping(source = "electronicAccessories", target = "electronicAccessories",ignore = true),
    })
    ContractManagement dto2po(ContractManagementReqDTO reqDTO);

    @Mappings({
            @Mapping(source = "affiliateCustomers", target = "affiliateCustomers"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "no", target = "no"),
            @Mapping(source = "amount", target = "amount"),
            @Mapping(source = "startDate", target = "startDate"),
            @Mapping(source = "endDate", target = "endDate"),
            @Mapping(source = "electronicAccessories", target = "electronicAccessories",ignore = true),
    })
    ContractManagementReqDTO po2dto(ContractManagement po);

    @Mappings({
            @Mapping(target = "electronicAccessories", expression = "java(String.valueOf(String.valueOf(map.get(\"electronicAccessories\"))))"),
            @Mapping(target = "entryPerson", expression = "java(String.valueOf(String.valueOf(map.get(\"entryPerson\"))))"),
            @Mapping(target = "entryTime", expression = "java(new java.util.Date(String.valueOf(map.get(\"entryTime\"))))"),
            @Mapping(target = "changeTime", expression = "java(new java.util.Date(String.valueOf(map.get(\"changeTime\"))))"),
            @Mapping(target = "auditStatus", expression = "java(Integer.valueOf(String.valueOf(map.get(\"auditStatus\"))))"),
            @Mapping(target = "toStamp", expression = "java(Integer.valueOf(String.valueOf(map.get(\"toStamp\"))))"),
            @Mapping(target = "invalid", expression = "java(Integer.valueOf(String.valueOf(map.get(\"invalid\"))))"),
    })
    void map2po(@MappingTarget ContractManagement contractManagement, Map<String, Object> map);
}
