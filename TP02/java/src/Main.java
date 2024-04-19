
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Data data = new Data();

		String caminhoDoArquivoCsv = "/tmp/characters.csv";
		data.lerCsv(caminhoDoArquivoCsv);

		// Q01.questao1(data, sc);
		Q03.questao3(data, sc);
		sc.close();
	}

	public static boolean isFim(String s) {
		return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
	}

}

class Q01 {

	public static void questao1(Data data, Scanner sc) {
		while (sc.hasNext()) {
			String input = sc.next();

			if (Main.isFim(input))
				break;

			Personagem personagem = data.getPersonagemById(input);

			if (!personagem.equals(null)) {
				System.out.println(personagem);
			}
		}
	}

}

class Q03 {

	public static void questao3(Data data, Scanner sc) {
		ArrayList<Personagem> personagens = new ArrayList<>();
		Personagem personagem = new Personagem();

		while (sc.hasNextLine()) {
			String input = sc.nextLine();

			if (Main.isFim(input))
				break;

			personagem = data.getPersonagemById(input);
			personagens.add(personagem);

		}

		while (sc.hasNextLine()) {
			String name = sc.nextLine();

			if (Main.isFim(name))
				break;

			isPersonagemOnList(name, personagens);
		}
	}

	private static void isPersonagemOnList(String name, ArrayList<Personagem> personagens) {
		boolean found = false;
		for (Personagem p : personagens) {
			if (p.getPersonagemName().equals(name)) {
				System.out.println("SIM");
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("NAO");
		}
	}

}

class Data {
	private List<Personagem> personagens = new ArrayList<>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	public Data() {
		super();
		this.personagens = new ArrayList<>();
	}

	public List<Personagem> getPersonagens() {
		return personagens;
	}

	public Personagem getPersonagemById(String idString) {
		UUID id = UUID.fromString(idString);

		for (Personagem p : personagens) {
			if (p.getId().equals(id)) {
				return p;
			}
		}
		return null;
	}

	public void lerCsv(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String linha;

			br.readLine();
			while ((linha = br.readLine()) != null) {

				try {
					Personagem p = instanciaPersonagemPeloCsv(linha);
					personagens.add(p);
				} catch (Exception e) {
					System.err.println("Erro ao processar a linha: " + linha);
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Personagem instanciaPersonagemPeloCsv(String linha) throws ParseException {
		String[] values = splitString(linha, ';');
		UUID id = UUID.fromString(values[0]);
		String name = values[1];

		String alternateNamesRaw = replaceAllSimple(values[2], "[", "");
		alternateNamesRaw = replaceAllSimple(alternateNamesRaw, "]", "");
		alternateNamesRaw = replaceAllSimple(alternateNamesRaw, "\"", "");

		ArrayList<String> alternateNames = new ArrayList<>(Arrays.asList(splitString(alternateNamesRaw, ',')));

		String house = values[3];
		String ancestry = values[4];
		String species = values[5];
		String patronus = values[6];
		boolean hogwartsStaff = values[7].equals("VERDADEIRO");
		String hogwartsStudent = values[8];
		String actorName = values[9];
		boolean alive = values[10].equalsIgnoreCase("VERDADEIRO");
		Date dateOfBirth = values[12].isEmpty() ? null : sdf.parse(values[12]);
		int yearOfBirth = Integer.parseInt(values[13]);
		String eyeColour = values[14];
		String gender = values[15];
		String hairColour = values[16];
		boolean wizard = values[17].equalsIgnoreCase("VERDADEIRO");

		return new Personagem(id.toString(), name, alternateNames, house, ancestry, species, patronus, hogwartsStaff,
				hogwartsStudent, actorName, alive, dateOfBirth, yearOfBirth, eyeColour, gender, hairColour, wizard);
	}

	public static String[] splitString(String input, char delimiter) {
		List<String> result = new ArrayList<>();
		StringBuilder buffer = new StringBuilder();

		for (char ch : input.toCharArray()) {
			if (ch == delimiter) {
				result.add(buffer.toString());
				buffer = new StringBuilder();
			} else {
				buffer.append(ch);
			}
		}
		if (buffer.length() > 0) {
			result.add(buffer.toString());
		}
		return result.toArray(new String[0]);
	}

	public static String replaceAllSimple(String input, String toReplace, String replacement) {
		StringBuilder result = new StringBuilder();
		int start = 0;
		int end = input.indexOf(toReplace);
		while (end != -1) {
			result.append(input, start, end).append(replacement);
			start = end + toReplace.length();
			end = input.indexOf(toReplace, start);
		}
		result.append(input.substring(start));
		return result.toString();
	}
}

class Personagem {

	private UUID _id;
	private int _yearOfBirth;
	private String _name;
	private ArrayList<String> _alternateNames;
	private String _house;
	private String _ancestry;
	private String _species;
	private String _patronus;
	private String _hogwartsStudent;
	private String _actorName;
	private String _eyeColour;
	private String _gender;
	private String _hairColour;
	private Date _dateOfBirth;
	private boolean _hogwartsStaff;
	private boolean _alive;
	private boolean _wizard;

	public Personagem() {
	}

	public Personagem(String id, String name, ArrayList<String> alternateNames, String house, String ancestry,
			String species, String patronus, boolean hogwartsStaff, String hogwartsStudent, String actorName,
			boolean alive, Date dateOfBirth, int yearOfBirth, String eyeColour, String gender, String hairColour,
			boolean wizard) {

		_id = UUID.fromString(id);
		_yearOfBirth = yearOfBirth;
		_name = name;
		_alternateNames = alternateNames;
		_house = house;
		_ancestry = ancestry;
		_species = species;
		_patronus = patronus;
		_hogwartsStudent = hogwartsStudent;
		_actorName = actorName;
		_eyeColour = eyeColour;
		_gender = gender;
		_hairColour = hairColour;
		_dateOfBirth = dateOfBirth;
		_hogwartsStaff = hogwartsStaff;
		_alive = alive;
		_wizard = wizard;
	}

	public static Personagem getPersonagem() {
		return new Personagem();
	}

	public UUID getId() {
		return _id;
	}

	public String getPersonagemName() {
		return _name;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = (_dateOfBirth != null) ? sdf.format(_dateOfBirth) : "";

		String alternateNamesFormatted = Data.replaceAllSimple(
				Data.replaceAllSimple(Data.replaceAllSimple(this._alternateNames.toString(), "[", ""), "]", ""), "'",
				"");

		return String.format(
				"[%s ## %s ## {%s} ## %s ## %s ## %s ## %s ## %b ## %b ## %s ## %b ## %s ## %d ## %s ## %s ## %s ## %b]",
				this._id, this._name, alternateNamesFormatted, this._house, this._ancestry, this._species,
				this._patronus, this._hogwartsStaff, this._hogwartsStudent.equalsIgnoreCase("VERDADEIRO"),
				this._actorName, this._alive, formattedDate, this._yearOfBirth, this._eyeColour, this._gender,
				this._hairColour, this._wizard);
	}
}