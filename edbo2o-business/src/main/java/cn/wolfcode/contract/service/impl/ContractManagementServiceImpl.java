package cn.wolfcode.contract.service.impl;

import cn.wolfcode.common.utils.SecurityUtils;
import cn.wolfcode.common.utils.file.FileUploadUtils;
import cn.wolfcode.contract.domain.ContractManagement;
import cn.wolfcode.contract.domain.dto.ContractManagementReqDTO;
import cn.wolfcode.contract.mapper.ContractManagementMapper;
import cn.wolfcode.contract.service.IContractManagementService;
import cn.wolfcode.utils.MapStructs;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 合同管理Service业务层处理
 *
 * @author YL
 * @date 2023-06-16
 */
@Service
public class ContractManagementServiceImpl extends ServiceImpl<ContractManagementMapper, ContractManagement> implements IContractManagementService {
    @Autowired
    private ContractManagementMapper contractManagementMapper;


    /**
     * 查询合同管理
     *
     * @param id 合同管理主键
     * @return 合同管理
     */
    @Override
    public ContractManagement selectContractManagementById(Long id) {
        return contractManagementMapper.selectContractManagementById(id);
    }

    /**
     * 查询合同管理列表
     *
     * @param contractManagement 合同管理
     * @return 合同管理
     */
    @Override
    public List<ContractManagement> selectContractManagementList(ContractManagement contractManagement) {
        return contractManagementMapper.selectContractManagementList(contractManagement);
    }

    /**
     * 新增合同管理
     *
     * @param contractManagementReqDTO 合同管理
     * @return 结果
     */
    @Override
    public int insertContractManagement(ContractManagementReqDTO contractManagementReqDTO) throws IOException {
        LocalDateTime date = LocalDateTime.now();
        //文件路径
        String filePath = date.getYear() + "/" + date.getMonth().getValue() + "/" + date.getDayOfMonth() + "/";

        //创建文件夹
        Files.createDirectories(Paths.get(filePath));
        //文件名称
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString().replace("-", "") + "." + FileUploadUtils.getExtension(contractManagementReqDTO.getElectronicAccessories());

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(new File(filePath + fileName), "rw")) {
            FileChannel channel = randomAccessFile.getChannel();
            channel.write(ByteBuffer.wrap(contractManagementReqDTO.getElectronicAccessories().getBytes()));
        }
        ContractManagement contractManagement = MapStructs.INSTANCE.dto2po(contractManagementReqDTO);
        MapStructs.INSTANCE.map2po(contractManagement, new HashMap<String, Object>() {{
            //保存文件上传路径
            this.put("electronicAccessories", filePath + fileName);
            this.put("entryPerson", SecurityUtils.getLoginUser().getUsername());
            this.put("entryTime", new Date());
            this.put("changeTime", new Date());
            this.put("auditStatus", ContractManagement.AUDIT_EDIT);
            this.put("toStamp", ContractManagement.NOT_STAMP);
            this.put("invalid", ContractManagement.NOT_IS_INVALID);
        }});
        return contractManagementMapper.insertContractManagement(contractManagement);

    }

    @Override
    public void auditSuccess(Long id) {
        ContractManagement contractManagement = contractManagementMapper.selectContractManagementById(id);
        contractManagement.setAuditStatus(ContractManagement.AUDIT_FAIL);
        contractManagementMapper.updateContractManagement(contractManagement);
    }

    @Override
    public void auditPass(Long id) {
        ContractManagement contractManagement = contractManagementMapper.selectContractManagementById(id);
        contractManagement.setAuditStatus(ContractManagement.AUDIT_PASS);
        contractManagementMapper.updateContractManagement(contractManagement);
    }

    @Override
    public void toStamp(Long id) {
        ContractManagement contractManagement = contractManagementMapper.selectContractManagementById(id);
        contractManagement.setToStamp(ContractManagement.IS_STAMP);
        contractManagementMapper.updateContractManagement(contractManagement);
    }


    @Override
    public void invalid(Long id) {
        ContractManagement contractManagement = contractManagementMapper.selectContractManagementById(id);
        contractManagement.setInvalid(ContractManagement.IS_INVALID);
        contractManagementMapper.updateContractManagement(contractManagement);
    }

    /**
     * 修改合同管理
     *
     * @param contractManagementReqDTO 合同管理
     * @return 结果
     */
    @Override
    public int updateContractManagement(ContractManagementReqDTO contractManagementReqDTO) throws Exception {
        LocalDateTime date = LocalDateTime.now();
        //文件路径
        String filePath = date.getYear() + "/" + date.getMonth().getValue() + "/" + date.getDayOfMonth() + "/";

        //创建文件夹
        Files.createDirectories(Paths.get(filePath));
        //文件名称
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString().replace("-", "") + "." + FileUploadUtils.getExtension(contractManagementReqDTO.getElectronicAccessories());

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(new File(filePath + fileName), "rw")) {
            FileChannel channel = randomAccessFile.getChannel();
            channel.write(ByteBuffer.wrap(contractManagementReqDTO.getElectronicAccessories().getBytes()));
        }
        ContractManagement contractManagement = MapStructs.INSTANCE.dto2po(contractManagementReqDTO);
        MapStructs.INSTANCE.map2po(contractManagement, new HashMap<String, Object>() {{
            //保存文件上传路径
            this.put("electronicAccessories", filePath + fileName);
            this.put("entryPerson", SecurityUtils.getLoginUser().getUsername());
            this.put("entryTime", new Date());
            this.put("changeTime", new Date());
            this.put("auditStatus", ContractManagement.AUDIT_EDIT);
            this.put("toStamp", ContractManagement.NOT_STAMP);
            this.put("invalid", ContractManagement.NOT_IS_INVALID);
        }});
        return contractManagementMapper.updateContractManagement(contractManagement);
    }

    /**
     * 批量删除合同管理
     *
     * @param ids 需要删除的合同管理主键
     * @return 结果
     */
    @Override
    public int deleteContractManagementByIds(Long[] ids) {
        return contractManagementMapper.deleteContractManagementByIds(ids);
    }

    /**
     * 删除合同管理信息
     *
     * @param id 合同管理主键
     * @return 结果
     */
    @Override
    public int deleteContractManagementById(Long id) {
        return contractManagementMapper.deleteContractManagementById(id);
    }
}
