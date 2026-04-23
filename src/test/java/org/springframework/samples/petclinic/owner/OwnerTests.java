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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class OwnerTests {

	@Test
	void shouldAddNewPet() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		owner.addPet(pet);
		assertThat(owner.getPets()).hasSize(1);
	}

	@Test
	void shouldNotAddExistingPet() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setId(1);
		owner.addPet(pet);
		assertThat(owner.getPets()).isEmpty();
	}

	@Test
	void shouldGetPetByName() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);
		assertThat(owner.getPet("Buddy")).isEqualTo(pet);
		assertThat(owner.getPet("buddy")).isEqualTo(pet);
	}

	@Test
	void shouldReturnNullForUnknownPetName() {
		Owner owner = new Owner();
		assertThat(owner.getPet("Unknown")).isNull();
	}

	@Test
	void shouldGetPetByNameIgnoringNew() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);
		assertThat(owner.getPet("Buddy", true)).isNull();

		pet.setId(1);
		assertThat(owner.getPet("Buddy", true)).isEqualTo(pet);
	}

	@Test
	void shouldReturnNullForPetWithNullName() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		owner.addPet(pet);
		assertThat(owner.getPet("Buddy")).isNull();
	}

	@Test
	void shouldGetPetById() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);
		pet.setId(1);
		assertThat(owner.getPet(Integer.valueOf(1))).isEqualTo(pet);
	}

	@Test
	void shouldReturnNullForUnknownPetId() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);
		pet.setId(1);
		assertThat(owner.getPet(Integer.valueOf(99))).isNull();
	}

	@Test
	void shouldReturnNullForNewPetById() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);
		assertThat(owner.getPet(Integer.valueOf(1))).isNull();
	}

	@Test
	void shouldReturnToString() {
		Owner owner = new Owner();
		owner.setId(1);
		owner.setFirstName("Joe");
		owner.setLastName("Bloggs");
		owner.setAddress("123 Street");
		owner.setCity("London");
		owner.setTelephone("1234567890");
		String result = owner.toString();
		assertThat(result).contains("Joe");
		assertThat(result).contains("Bloggs");
	}

	@Test
	void shouldAddVisitToPet() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);
		pet.setId(1);

		Visit visit = new Visit();
		visit.setDescription("checkup");
		owner.addVisit(1, visit);

		assertThat(pet.getVisits()).hasSize(1);
	}

	@Test
	void shouldThrowWhenAddVisitWithNullPetId() {
		Owner owner = new Owner();
		Visit visit = new Visit();
		assertThatIllegalArgumentException().isThrownBy(() -> owner.addVisit(null, visit));
	}

	@Test
	void shouldThrowWhenAddVisitWithNullVisit() {
		Owner owner = new Owner();
		assertThatIllegalArgumentException().isThrownBy(() -> owner.addVisit(1, null));
	}

	@Test
	void shouldThrowWhenAddVisitWithInvalidPetId() {
		Owner owner = new Owner();
		Visit visit = new Visit();
		assertThatIllegalArgumentException().isThrownBy(() -> owner.addVisit(99, visit));
	}

	@Test
	void shouldGetAndSetProperties() {
		Owner owner = new Owner();
		owner.setAddress("123 Street");
		owner.setCity("London");
		owner.setTelephone("1234567890");
		assertThat(owner.getAddress()).isEqualTo("123 Street");
		assertThat(owner.getCity()).isEqualTo("London");
		assertThat(owner.getTelephone()).isEqualTo("1234567890");
	}

}
