package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@SpringBootApplication
public class DemoDataApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicacion arrancada ...");
	}

	@Bean
	CommandLineRunner demoJDBC(JdbcTemplate db) {
		return args -> {
			record Vet(int id, String firstName, String lastName) {}
			class VetRowMapper implements RowMapper<Vet> {
				@Override
				public Vet mapRow(ResultSet row, int index) throws SQLException {
					return new Vet(row.getInt(1), row.getString("first_name"), row.getString(3));
				}
				
			}
			try {
				System.out.println(">>> Nº Veterinarios: " + db.queryForObject("select count(*) from vets", Integer.class));
				System.out.println(">>> Listado inicial");
				db.query("select * from vets", new VetRowMapper()).forEach(System.out::println);
				var id = 2;
				var vet = db.queryForObject(
						"select * from vets where id=?", 
						(row, index) -> new Vet(row.getInt(1), row.getString("first_name"), row.getString(3)), 
						2);
				System.out.println("Consulta id: %d => %s".formatted(id, vet));
				var rows = db.update(
						"insert into vets(first_name,last_name) values(?, ?)", 
						"Pepito", "Grillo");
				System.out.println(">>> Insertados: " + rows);
				var newId =  db.queryForObject("select max(id) from vets", Integer.class);
				vet = db.queryForObject(
						"select * from vets where id=?", 
						new VetRowMapper()
						, newId);
				System.out.println(">>> Consulta nuevo: %d => %s".formatted(newId, vet));
				rows = db.update(
						"update vets set first_name = ?, last_name = ? where id=?", 
						vet.firstName().toUpperCase(), vet.lastName().toUpperCase(), vet.id());
				System.out.println(">>> Modificados: " + rows);
				System.out.println(">>> Listado actual");
				db.query("select * from vets", new VetRowMapper()).forEach(System.out::println);
				rows = db.update("delete vets where id=?", vet.id());
				System.out.println(">>> Borrados: " + rows);
				System.out.println(">>> Sobreviven: ");
				db.query("select * from vets", new VetRowMapper()).forEach(System.out::println);
				System.out.println(">>> Fallo forzado ");
				rows = db.update("delete vets where id=?", 2);
			} catch (DataAccessException e) {
				System.err.println(e.getMessage());
			}
		};
	}

}
