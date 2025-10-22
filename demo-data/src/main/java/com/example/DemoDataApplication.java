package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;

@SpringBootApplication
public class DemoDataApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicacion arrancada ...");
	}

//	@Bean
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
						"select * from vets where id = ?", // "select * from vets where id=" + id
						(row, index) -> new Vet(row.getInt(1), row.getString("first_name"), row.getString(3)), 
						id);
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
//				var idDel = "2 OR 1=1; DROP TABLE ...";
//				rows = db.update("delete vets where id="+idDel);
				rows = db.update("delete vets where id=?", 2);
			} catch (DataAccessException e) {
				System.err.println(e.getMessage());
			}
		};
	}
	
//	@Bean
	CommandLineRunner demoJDBC(NamedParameterJdbcTemplate db) {
		return args -> {
			record Vet(int id, String firstName, String lastName) {}
			class VetRowMapper implements RowMapper<Vet> {
				@Override
				public Vet mapRow(ResultSet row, int index) throws SQLException {
					return new Vet(row.getInt(1), row.getString("first_name"), row.getString(3));
				}
				
			}
			try {
				System.out.println(">>> Listado inicial");
				db.query("select * from vets", new VetRowMapper()).forEach(System.out::println);
				Map<String, Object> parametros = new HashMap<>();
				parametros.put("id", 2);
				var vet = db.queryForObject(
						"select * from vets where id=:id", 
						parametros,
						(row, index) -> new Vet(row.getInt(1), row.getString("first_name"), row.getString(3)));
				System.out.println("Consulta id: %d => %s".formatted(parametros.get("id"), vet));
				var rows = db.update("insert into vets(first_name,last_name) values(:nombre, :apellidos)", 
						Map.of("nombre", "Pepito", "apellidos", "Grillo"));
				System.out.println(">>> Insertados: " + rows);
				vet = db.queryForObject(
						"select * from vets where id=:id",
						Map.of("id", 2),
						new VetRowMapper());
				rows = db.update(
						"update vets set first_name = :nombre, last_name = :apellidos where id=:id", 
						Map.of("id", vet.id(), "nombre", vet.firstName().toUpperCase(), "apellidos", vet.lastName().toUpperCase()));
				System.out.println(">>> Modificados: " + rows);
				System.out.println(">>> Listado actual");
				db.query("select * from vets", new VetRowMapper()).forEach(System.out::println);
			} catch (DataAccessException e) {
				System.err.println(e.getMessage());
			}
		};
	}
	
//	@Bean
	CommandLineRunner demoJdbcClient(JdbcClient db) {
		return args -> {
			record Vet(int id, String firstName, String lastName) {}
			try {
				System.out.println("Nº Veterinarios: " + db
						.sql("select count(*) from vets")
						.query(Integer.class)
						.single());
				System.out.println("Nº Veterinarios: " + db
						.sql("select * from vets where id=?")
						.param(3)
						.query(Vet.class)
						.single());
				var reg = new Vet(0, "Pepito", "Grillo");
				db.sql("insert into vets(first_name,last_name) values(:firstName, :lastName)")		
					.paramSource(reg)
					.update();
	
				db.sql("select * from vets")
					.query(Vet.class)
					.list()
					.forEach(System.out::println);
				System.out.println("Veterinario: " + db
						.sql("select * from vets where id=:id_vet")
						.param("id_vet", 6)
						.query(Vet.class)
						.single());
				
			} catch (DataAccessException e) {
				System.err.println(e.getMessage());
			}
		};
	}

//	@Bean
	CommandLineRunner soloConsulta(JdbcClient db) {
		return args -> {
			record Vet(int id, String firstName, String lastName) {}
			try {
				db.sql("select * from vets")
					.query(Vet.class)
					.list()
					.forEach(System.out::println);
			} catch (DataAccessException e) {
				System.err.println(e.getMessage());
			}
		};
	}

	@Bean
	CommandLineRunner ejemplosData(EjemplosSpringData demos) {
		return args -> {
			demos.run();
		};
	}

}
