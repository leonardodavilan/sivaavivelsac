package pe.com.avivel.sistemas.siva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SivaApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SivaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
/*		String password = "12345";
		for (int i = 0; i < 4; i++) {
			String passwordBcrypt = passwordEncoder.encode(password);
			System.out.println(passwordBcrypt);
		}

		double numero = 3.1416;
		System.out.printf("El número originalmente es: %f\n", numero);
		double parteDecimal = numero % 1; // Lo que sobra de dividir al número entre 1
		double parteEntera = numero - parteDecimal; // Le quitamos la parte decimal usando una resta
		System.out.printf("Parte entera: %f. Parte decimal: %f\n", parteEntera, parteDecimal);
*/
/*
		@Data
		class PresentacionA{

			int id;
			String nombre;
			BigDecimal cantidad;

			PresentacionA(int id, String nombre, BigDecimal cantidad){
				this.id = id;
				this.nombre = nombre;
				this.cantidad = cantidad;
			}
		}


		ArrayList<PresentacionA> arrayObjetos = new ArrayList<>();

		arrayObjetos.add(new PresentacionA(32, "dosis x", BigDecimal.valueOf(4000)));
		arrayObjetos.add(new PresentacionA(14, "dosis x", BigDecimal.valueOf(2000)));
		arrayObjetos.add(new PresentacionA(13, "dosis x", BigDecimal.valueOf(1000)));

		double poblacion = 50180;
		int cantVacunas;
		double residuo;
		int idPresentacion = 14;

		Collections.sort(arrayObjetos, new Comparator<PresentacionA>() {

			@Override
			public int compare(PresentacionA p1, PresentacionA p2) {
				return new BigDecimal(String.valueOf(p2.getCantidad())).compareTo(new BigDecimal(String.valueOf(p1.getCantidad())));
			}

		});

		ArrayList<VacunaCalculadaQueryDTO> arrayObjetosNew = new ArrayList<>();
		int diferencia = 0;

		for (int i=0;i<arrayObjetos.size();i++){

			int cantidadPresentacion =  arrayObjetos.get(i).getCantidad().intValue();

			if(poblacion> cantidadPresentacion){

				cantVacunas = (int) poblacion / cantidadPresentacion;
				residuo = poblacion % cantidadPresentacion;

				//System.out.println(residuo);

				if(residuo == 0 ){
					System.out.println(cantidadPresentacion);
					System.out.println(cantVacunas);
					System.out.println(residuo);

					arrayObjetosNew.add(new VacunaCalculadaQueryDTO(arrayObjetos.get(i).getId(),BigDecimal.valueOf(cantVacunas),0));

					break;
				}else{
					if(i == arrayObjetos.size()-1){
						cantVacunas = cantVacunas + 1;
						System.out.println(cantidadPresentacion);
						System.out.println(cantVacunas);
						System.out.println(residuo);

						diferencia =  cantidadPresentacion*cantVacunas - (int)poblacion;

						arrayObjetosNew.add(new VacunaCalculadaQueryDTO(arrayObjetos.get(i).getId(),BigDecimal.valueOf(cantVacunas),diferencia));
						break;
					}else{
						System.out.println(cantidadPresentacion);
						System.out.println(cantVacunas);
						System.out.println(residuo);

						arrayObjetosNew.add(new VacunaCalculadaQueryDTO(arrayObjetos.get(i).getId(),BigDecimal.valueOf(cantVacunas),0));
						poblacion = residuo;
					}
				}
			}else{
				cantVacunas = 1;
				System.out.println(cantidadPresentacion);
				System.out.println(cantVacunas);

				diferencia = cantidadPresentacion - (int)poblacion;
				arrayObjetosNew.add(new VacunaCalculadaQueryDTO(arrayObjetos.get(i).getId(),BigDecimal.valueOf(cantVacunas),diferencia));
				break;
			}

		}
		for (VacunaCalculadaQueryDTO item : arrayObjetosNew) {
			if (item.getIdPresentacion() == idPresentacion){
				System.out.println(item);
			}
		}
		System.out.println(arrayObjetosNew);
*/
	}
}