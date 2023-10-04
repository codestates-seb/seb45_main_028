package com.mainproject.be28.domain.shopping.order.data;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.amazonaws.services.ec2.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PROTECTED)
public class DeliveryInformation {
    @NotNull
    @Size(min = 2, max = 10)
    @Default
    private String name = "";
    @NotNull
    @Pattern(regexp = "01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    @Default
    private String phoneNumber = "";
    @Embedded
    @Default
    private Address address = new Address();

}