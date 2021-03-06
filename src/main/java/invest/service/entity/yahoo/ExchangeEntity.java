package invest.service.entity.yahoo;

import invest.service.dto.yahoo.ExchangeDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "usd_exchange")
public class ExchangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    UUID uuid;

    @Column(name = "ticker")
    private String ticker;

    @Column(name = "currency")
    private String name;

    @Column(name = "rate")
    private double rate;

    public ExchangeEntity(UUID uuid, ExchangeDto dto) {
        this.uuid = uuid;
        this.ticker = dto.getTicker();
        this.name = dto.getName();
        this.rate = dto.getRate();
    }




}
