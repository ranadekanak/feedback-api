package org.krsnaa.feedback.repository;

import org.krsnaa.feedback.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {

	Language findByLanguageAndKey(String language, String key);
}
