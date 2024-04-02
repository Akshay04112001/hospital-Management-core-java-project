
package Jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JdbcDemo {
	Connection con() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/HospitalDb";

			String username = "root";
			String password = "root";

			Connection con = DriverManager.getConnection(url, username, password);
			return con;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	int insert(String name, int age) {
		try {
			Connection con = con();

			String query = "insert into patient(name, age) values(?,?)";

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			ps.setInt(2, age);
			int res = ps.executeUpdate();
			return res;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	int delete(int id) {
		Connection connection = con();
		String sql = "delete from patient where id=?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);

			int response = preparedStatement.executeUpdate();
			return response;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	int update(int id, String name, int age) {
		Connection connection = con();
		String sql = "update patient set name=?,age=? where id=?";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setInt(3, id);

			int response = preparedStatement.executeUpdate();

			return response;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	void display() {
		try {
			Connection con = con();
			String query = "select * from patient";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("ID: " + resultSet.getInt("id") + "\tNAME: " + resultSet.getString("name")
						+ "\tAGE: " + resultSet.getInt("age"));
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	List<List<Object>> displayWithList() {
		Connection con = con();
		String query = "select * from patient";
		List<List<Object>> list = new ArrayList<>();

		try {
			PreparedStatement preparedStatement = con.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");

				List<Object> row = new ArrayList<>();
				row.add(id);
				row.add(name);
				row.add(age);

				list.add(row);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	int search(int id) {
		Connection connection = con();
		String sql = "select * from patient where id=?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("ID: " + resultSet.getInt("id") + "\tNAME: " + resultSet.getString("name")
						+ "\tAGE: " + resultSet.getInt("age"));
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	int searchByName(String startingLetter) {
		Connection connection = con();
		String sql = "select * from patient where name like ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, startingLetter + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("ID: " + resultSet.getInt("id") + "\tNAME: " + resultSet.getString("name")
						+ "\tAGE: " + resultSet.getInt("age"));
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		JdbcDemo obj = new JdbcDemo();
		System.out.println("===========================Government Hospital===========================");
		System.out.println();
		System.out.println("=========================Choose Following Numbers=========================");
		System.out.println();
		while (true) {
			System.out.println(
					"1].ADD patient name\t2].DELETE patient name \t3].UPDATE patient name\n4].DISPLAY All patients \t5].DISPLAY Patients with LIST\t6].SEARCH by ID\n7].SEARCH Patient by starting letter\t0].EXIT");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1: {
				System.out.println("Enter name: ");
				String name = scanner.next();
				System.out.println("Enter age: ");
				int age = scanner.nextInt();
				int response = obj.insert(name, age);
				if (response > 0) {
					System.out.println("data inserted successfully");
				} else {
					System.out.println("0 row affected");
				}
				break;
			}
			case 2: {
				System.out.println("Enter id for delete: ");
				int id = scanner.nextInt();
				int response = obj.delete(id);
				if (response > 0) {
					System.out.println("Data deleted successfully for id=" + id);
				} else {
					System.out.println("0 row affected");
				}
				break;
			}
			case 3: {
				System.out.println("Enter Id: ");
				int id1 = scanner.nextInt();

				System.out.println("Enter name: ");
				String name1 = scanner.next();

				System.out.println("Enter age: ");
				int age1 = scanner.nextInt();

				int response = obj.update(id1, name1, age1);

				if (response > 0) {
					System.out.println("data updated successfully");
				} else {
					System.out.println("0 row affected");
				}
				break;
			}
			case 4: {
				obj.display();
				break;
			}
			case 5: {
				List<List<Object>> list = obj.displayWithList();
				System.out.println(list);
				break;
			}
			case 6: {
				System.out.println("Enter id");
				int id = scanner.nextInt();
				obj.search(id);
				break;
			}
			case 7: {
				System.out.println("Enter starting letter of the name:");
				String letter = scanner.next();
				obj.searchByName(letter);
				break;
			}
			case 0: {
				System.exit(0);
			}
			default:
				System.out.println("Invalid choice");
				break;
			}
		}
	}
}
