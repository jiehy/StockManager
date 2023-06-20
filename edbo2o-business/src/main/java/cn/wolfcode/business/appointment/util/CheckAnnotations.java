package cn.wolfcode.business.appointment.util;

import cn.wolfcode.common.utils.file.FileUploadUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckAnnotations.CheckVerify.class)
public @interface CheckAnnotations {
    CheckVerifyEnum type() default CheckVerifyEnum.NULL;

    long min() default 1;

    long max() default 1;

    String message() default "不能为空";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    enum CheckVerifyEnum {
        NULL(1, "为空校验"),
        PHONE(2, "手机号校验"),
        LICENSEPLATE(3, "车牌号校验");

        private int code;
        private String desc;

        CheckVerifyEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    class CheckVerify implements ConstraintValidator<CheckAnnotations, Object> {
        private CheckAnnotations checkAnnotations;
        private CheckVerifyEnum checkVerifyEnum;

        @Override
        public void initialize(CheckAnnotations checkAnnotations) {
            this.checkAnnotations = checkAnnotations;
            this.checkVerifyEnum = checkAnnotations.type();
        }

        @Override
        public boolean isValid(Object object, ConstraintValidatorContext context) {
            if (object instanceof MultipartFile) {
                MultipartFile multipartFile = (MultipartFile) object;
                String extension = FileUploadUtils.getExtension(multipartFile);
                if (!"pdf".equals(extension)) {
                    return setContext(context, "请上传正确的合同文件");
                }
            }

            if (object == null || "".equals(String.valueOf(object))) {
                return false;
            }

            if (CheckVerifyEnum.PHONE.equals(checkVerifyEnum)) {
                if (!RegexUtils.isPhoneLegal(object.toString())) {
                    return setContext(context, "手机号码错误");
                }
            }

            if (CheckVerifyEnum.LICENSEPLATE.equals(checkVerifyEnum)) {
                if (ObjectUtils.isEmpty(VehiclePlateNoUtil.getVehiclePlateNo(object.toString()))) {
                    return setContext(context, "车牌号码错误");
                }
            }
            return true;
        }

        private boolean setContext(ConstraintValidatorContext context, String msg) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
            return false;
        }
    }
}