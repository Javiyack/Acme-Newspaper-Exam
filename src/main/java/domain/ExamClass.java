package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "ticker, title, description")
})public class ExamClass extends DomainEntity {

	private String ticker;

	private String title;

	private String description;

	private Integer gauge;

	private Date moment;

	private Boolean draftMode;

	private Newspaper newspaper;
	
	private Administrator admin;

	@NotNull
	@ManyToOne(optional = false)
	public Administrator getAdmin() {
		return admin;
	}

	public void setAdmin(Administrator admin) {
		this.admin = admin;
	}

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^\\d\\d([0][1-9]|[1][0-2])([012][0-9]|[3][01])-[A-Z]{4}$") // "YYMMDD-XXXX"
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	@Range(min = 1, max = 3)
	public Integer getGauge() {
		return gauge;
	}

	public void setGauge(Integer gauge) {
		this.gauge = gauge;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	public Boolean getDraftMode() {
		return draftMode;
	}

	public void setDraftMode(Boolean draftMode) {
		this.draftMode = draftMode;
	}

	
	@ManyToOne(optional = true)
	public Newspaper getNewspaper() {
		return newspaper;
	}

	public void setNewspaper(Newspaper newspaper) {
		this.newspaper = newspaper;
	}

}
