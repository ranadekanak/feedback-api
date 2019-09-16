package org.krsnaa.feedback.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "language")
@EntityListeners(AuditingEntityListener.class)
public class Language extends Audit {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
	
	@NotBlank
	@Column(name = "Lang")
	private String language;

	@NotBlank
	@Column(name = "MessageKey")
	private String key;

	@NotBlank
	@Column(name = "MessageValue")
	private String value;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
