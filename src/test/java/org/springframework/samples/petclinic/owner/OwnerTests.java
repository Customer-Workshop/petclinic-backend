/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test class for {@link Owner}
 */
class OwnerTests {

	private Owner owner;

	@BeforeEach
	void setup() {
		owner = new Owner();
		owner.setId(1);
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("6085551023");
	}

	@Test
	void testGetPetByName() {
		Pet pet = new Pet();
		pet.setName("Fido");
		owner.addPet(pet);
		assertThat(owner.getPet("Fido")).isEqualTo(pet);
		assertThat(owner.getPet("fido")).isEqualTo(pet);
		assertThat(owner.getPet("Unknown")).isNull();
	}

	@Test
	void testGetPetByNameIgnoreNew() {
		Pet newPet = new Pet();
		newPet.setName("NewPet");
		owner.addPet(newPet);

		assertThat(owner.getPet("NewPet", false)).isEqualTo(newPet);
		assertThat(owner.getPet("NewPet", true)).isNull();

		newPet.setId(5);
		assertThat(owner.getPet("NewPet", true)).isEqualTo(newPet);
	}

	@Test
	void testGetPetById() {
		Pet pet = new Pet();
		pet.setName("Max");
		owner.addPet(pet);
		pet.setId(10);

		assertThat(owner.getPet(10)).isEqualTo(pet);
		assertThat(owner.getPet(99)).isNull();
	}

	@Test
	void testGetPetByIdSkipsNewPets() {
		Pet newPet = new Pet();
		newPet.setName("Buddy");
		owner.addPet(newPet);
		assertThat(owner.getPet((Integer) null)).isNull();
	}

	@Test
	void testAddPetOnlyAddsNewPets() {
		Pet pet = new Pet();
		pet.setName("Rex");
		owner.addPet(pet);
		assertThat(owner.getPets()).hasSize(1);

		pet.setId(1);
		owner.addPet(pet);
		assertThat(owner.getPets()).hasSize(1);
	}

	@Test
	void testAddVisit() {
		Pet pet = new Pet();
		pet.setName("Bella");
		owner.addPet(pet);
		pet.setId(7);

		Visit visit = new Visit();
		visit.setDescription("checkup");
		owner.addVisit(7, visit);

		assertThat(pet.getVisits()).hasSize(1);
	}

	@Test
	void testAddVisitWithNullPetId() {
		assertThatThrownBy(() -> owner.addVisit(null, new Visit())).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void testAddVisitWithNullVisit() {
		Pet pet = new Pet();
		pet.setName("Test");
		owner.addPet(pet);
		pet.setId(1);
		assertThatThrownBy(() -> owner.addVisit(1, null)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void testAddVisitWithInvalidPetId() {
		assertThatThrownBy(() -> owner.addVisit(999, new Visit())).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void testToString() {
		String result = owner.toString();
		assertThat(result).contains("Franklin");
		assertThat(result).contains("George");
		assertThat(result).contains("Madison");
	}

	@Test
	void testGetPetWithNullName() {
		Pet pet = new Pet();
		owner.addPet(pet);
		assertThat(owner.getPet("anyName")).isNull();
	}

}
