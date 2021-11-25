package db;

import gui.*;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {
    Queries queries;
    Window window;
    SideMenu menu;
    Login login;
    Central editPanel;
    Tables selectPanel;

    Form estadosAuto;
    Form marcas;
    Form modelos;
    Form autos;
    Form estados;
    Form ciudades;
    Form colonias;
    Form sucursales;
    Form logAdmin;
    Form empleados;
    Form clientes;
    Form rentas;

    public App() {
        window = new Window("Renta de autos");

        menu = new SideMenu("Iniciar sesión", "Tablas", "Consultar");
        login = new Login(Login.SPANISH);

        estadosAuto = new Form("ID Estado", "Descripción");
        marcas = new Form("ID Marca", "Nombre");
        modelos = new Form("ID Modelo", "ID Marca", "Nombre", "Precio Dia", "Precio Garantía");
        autos = new Form("Placa", "ID Modelo", "ID Estado", "Capacidad", "Disponible");
        estados = new Form("ID Estado", "Nombre");
        ciudades = new Form("ID Ciudad", "ID Estado", "Nombre");
        colonias = new Form("ID Colonia", "ID Estado", "ID Ciudad", "Nombre");
        sucursales = new Form("ID Sucursal", "ID Estado", "ID Ciudad", "Nombre", "ID Colonia", "Calle");
        logAdmin = new Form("Usuario", "Contraseña");
        empleados = new Form("ID Empleado", "ID Sucursal", "Nombre", "Apellido Paterno", "Apellido Materno", "Teléfono", "Correo");
        clientes = new Form("No. Licencia", "Nombre", "Apellido Paterno", "Apellido Materno", "Teléfono", "Correo", "ID Colonia", "ID Estado", "ID Ciudad", "Calle", "No. Casa");
        rentas = new Form("ID Renta", "Placa", "ID Empleado", "No. Licencia", "Fecha Préstamo", "Fecha Devolución", "Dias Renta", "Monto Total", "Monto total garantía");

        editPanel = new Central(
                estadosAuto,
                marcas,
                modelos,
                autos,
                estados,
                ciudades,
                colonias,
                sucursales,
                logAdmin,
                empleados,
                clientes,
                rentas);
        editPanel.setVisibleOnly(autos);
//        tablesPanel.setVisibleOnly(estadosAuto.dltPanel);

        String[] tablas = {"EstadosAuto", "Marcas", "Modelos", "Autos", "Estados", "Ciudades", "Colonias", "Sucursales", "Administradores", "Empleados", "Clientes", "Rentas"};

        editPanel.addList(tablas);

        editPanel.list.setSelectedIndex(3);

        editPanel.list.addListSelectionListener(e -> {
            switch (editPanel.list.getSelectedIndex()) {
                case 0 -> editPanel.setVisibleOnly(estadosAuto);
                case 1 -> editPanel.setVisibleOnly(marcas);
                case 2 -> editPanel.setVisibleOnly(modelos);
                case 3 -> editPanel.setVisibleOnly(autos);
                case 4 -> editPanel.setVisibleOnly(estados);
                case 5 -> editPanel.setVisibleOnly(ciudades);
                case 6 -> editPanel.setVisibleOnly(colonias);
                case 7 -> editPanel.setVisibleOnly(sucursales);
                case 8 -> editPanel.setVisibleOnly(logAdmin);
                case 9 -> editPanel.setVisibleOnly(empleados);
                case 10 -> editPanel.setVisibleOnly(clientes);
                case 11 -> editPanel.setVisibleOnly(rentas);
            }
        });

        // Lógica SQL
        queries = new Queries();
        selectPanel = new Tables(tablas);

        // Consultas
        selectPanel.getComboTablas().addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            switch (cb.getSelectedIndex()) {
                case 0 -> {
                    try {
                        selectPanel.fillTable(queries.buildTableModel(queries.execute("select * from estadosAuto")));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(window, ex.getMessage());
                    }
                }
                case 1 -> {
                    try {
                        selectPanel.fillTable(queries.buildTableModel(queries.execute("select * from marcas")));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(window, ex.getMessage());
                    }
                }
                case 2 -> {
                    try {
                        selectPanel.fillTable(queries.buildTableModel(queries.execute(
                                "select idModelo, nombreModelo NOMBRE, nombreMarca MARCA, precioDia, precioGarantia from modelos mo, marcas ma " +
                                        "where mo.idMarca = ma.idMarca"
                        )));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(window, ex.getMessage());
                    }
                }
                case 3 -> {
                    try {
                        selectPanel.fillTable(queries.buildTableModel(queries.execute(
                                "select placa, nombreModelo MODELO, descripcion ESTADO, capacidad, disponible " +
                                        "from autos, modelos, estadosAuto " +
                                        "where autos.idModelo = modelos.idModelo and autos.idEstadoAuto = estadosAuto.idEstadoAuto"
                        )));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(window, ex.getMessage());
                    }
                }
                case 4 -> {
                    try {
                        selectPanel.fillTable(queries.buildTableModel(queries.execute("select * from estados")));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(window, ex.getMessage());
                    }
                }
                case 5 -> {
                    try {
                        selectPanel.fillTable(queries.buildTableModel(queries.execute(
                                "select idCiudad, nombreCiudad NOMBRE, nombreEstado ESTADO from ciudades c, estados e " +
                                        "where c.idEstado = e.idEstado")));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(window, ex.getMessage());
                    }
                }
                case 6 -> {
                    try {
                        selectPanel.fillTable(queries.buildTableModel(queries.execute(
                                "select idColonia, nombreColonia NOMBRE, nombreCiudad CIUDAD, nombreEstado ESTADO from colonias col, ciudades ciu, estados est " +
                                "where col.idCiudad = ciu.idCiudad and col.idEstado = ciu.idEstado and col.idEstado = est.idEstado"
                        )));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(window, ex.getMessage());
                    }
                }
                case 7 -> {
                    try {
                        selectPanel.fillTable(queries.buildTableModel(queries.execute(
                                "select idSucursal, nombreSucursal NOMBRE, calle, nombreColonia COLONIA, nombreCiudad CIUDAD, nombreEstado ESTADO " +
                                "from sucursales s, colonias col, ciudades c, estados e " +
                                "where s.idColonia = col.idColonia and s.idCiudad = col.idCiudad and s.idEstado = col.idEstado and " +
                                "col.idCiudad = c.idCiudad and col.idEstado = c.idEstado and c.idEstado = e.idEstado"
                        )));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(window, ex.getMessage());
                    }
                }
                case 8 -> {
                    try {
                        selectPanel.fillTable(queries.buildTableModel(queries.execute("select * from logAdministradores")));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(window, ex.getMessage());
                    }
                }
                case 9 -> {
                    try {
                        selectPanel.fillTable(queries.buildTableModel(queries.execute(
                                "select idEmpleado, nombreEmp NOMBRE, paternoEmp PATERNO, maternoEmp MATERNO, nombreSucursal SUCURSAL, telefonoEmp TELEFONO, correoEmp CORREO " +
                                "from empleados e, sucursales s where e.idSucursal = s.idSucursal"
                        )));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(window, ex.getMessage());
                    }
                }
                case 10 -> {
                    try {
                        selectPanel.fillTable(queries.buildTableModel(queries.execute(
                                "select noLicencia, nombreCli NOMBRE, paternoCli PATERNO, maternoCli MATERNO, telefonoCli TELEFONO, correoCli CORREO, calle, noCasa," +
                                "nombreColonia COLONIA, nombreCiudad CIUDAD, nombreEstado ESTADO from clientes cli, colonias col, estados est, ciudades ciu " +
                                "where cli.idColonia = col.idColonia and cli.idEstado = col.idEstado and cli.idCiudad = col.idCiudad and " +
                                "cli.idCiudad = ciu.idCiudad and cli.idEstado = ciu.idEstado and cli.idEstado = est.idEstado"
                        )));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(window, ex.getMessage());
                    }
                }
                case 11 -> {
                    try {
                        selectPanel.fillTable(queries.buildTableModel(queries.execute(
                                "select idRenta, a.placa AUTO, nombreEmp EMPLEADO, nombreCli CLIENTE, fechaPrestamo, fechaDevolucion, " +
                                        "diasRenta, montoTotal TOTAL, montoTotalGarantia GARANTÍA from rentas r, autos a, empleados e, clientes c " +
                                        "where r.placa = a.placa and e.idEmpleado = r.idEmpleado and c.noLicencia = r.noLicencia"
                        )));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(window, ex.getMessage());
                    }
                }
            }
        });


        // Inserts
        estadosAuto.getInsBtn().addActionListener(e -> {
            queries.execute("insert into estadosAuto values (" +
                    estadosAuto.getInsertedValue("ID Estado") + ", '" +
                    estadosAuto.getInsertedValue("Descripción") + "')");
            estadosAuto.reset();
        });
        autos.getInsBtn().addActionListener(e -> {
            String query = "insert into autos values ('" + autos.getInsertedValue("Placa") + "', " +
                    autos.getInsertedValue("ID Modelo") + ", " + autos.getInsertedValue("ID Estado") +
                    ", " + autos.getInsertedValue("Capacidad") + ", '" + autos.getInsertedValue("Disponible")
                    + "')";
            System.out.println(query);
            queries.execute(query);
            autos.reset();
        });
        logAdmin.getInsBtn().addActionListener(e -> {
            queries.execute(
                    "insert into logAdministradores values ('" + logAdmin.getInsertedValue("Usuario") + "', '" +
                            logAdmin.getInsertedValue("Contraseña") + "')"
            );
            logAdmin.reset();
        });
        rentas.getInsBtn().addActionListener(e -> {
            String query = "insert into rentas values (" + rentas.getInsertedValue("ID Renta") +
                    ", '" + rentas.getInsertedValue("Placa") + "', " + rentas.getInsertedValue("ID Empleado") +
                    ", " + rentas.getInsertedValue("No. Licencia") + ", '" + rentas.getInsertedValue("Fecha Préstamo") +
                    "', '" + rentas.getInsertedValue("Fecha Devolución") + "', " + rentas.getInsertedValue("Dias Renta") +
                    ", " + rentas.getInsertedValue("Monto Total") + ", " + rentas.getInsertedValue("Monto total garantía") +
                    ")";
            System.out.println(query);
            queries.execute(query);
            rentas.reset();
        });
        marcas.getInsBtn().addActionListener(e -> {
            String query = "insert into marcas values (" + marcas.getInsertedValue("ID Marca") + ", '" +
                    marcas.getInsertedValue("Nombre") + "')";
            System.out.println(query);
            queries.execute(query);
            marcas.reset();
        });
        modelos.getInsBtn().addActionListener(e -> {
            String query = "insert into modelos values (" + modelos.getInsertedValue("ID Modelo") + ", " +
                    modelos.getInsertedValue("ID Marca") + ", '" + modelos.getInsertedValue("Nombre") + "', " +
                    modelos.getInsertedValue("Precio Dia") + ", " + modelos.getInsertedValue("Precio Garantía") +
                    ")";
            System.out.println(query);
            queries.execute(query);
            modelos.reset();
        });
        clientes.getInsBtn().addActionListener(e -> {
            String query = "insert into clientes values (" + clientes.getInsertedValue("No. Licencia") + ", '" +
                    clientes.getInsertedValue("Nombre") + "', '" + clientes.getInsertedValue("Apellido Paterno") + "', '" +
                    clientes.getInsertedValue("Apellido Materno") + "', " + clientes.getInsertedValue("Teléfono") + ", '" +
                    clientes.getInsertedValue("Correo") + "', " + clientes.getInsertedValue("ID Colonia") + ", " +
                    clientes.getInsertedValue("ID Estado") + ", " + clientes.getInsertedValue("ID Ciudad") + ", '" +
                    clientes.getInsertedValue("Calle") + "', '" + clientes.getInsertedValue("No. Casa") + "')";
            System.out.println(query);
            queries.execute(query);
            clientes.reset();
        });
        ciudades.getInsBtn().addActionListener(e -> {
            String query = "insert into ciudades values (" + ciudades.getInsertedValue("ID Estado") + ", " + ciudades.getInsertedValue("ID Ciudad") + ", '" +
                    ciudades.getInsertedValue("Nombre") + "')";
            System.out.println(query);
            queries.execute(query);
            ciudades.reset();
        });
        estados.getInsBtn().addActionListener(e -> {
            String query = "insert into Estados values (" + estados.getInsertedValue("ID Estado") + ", '" +
                    estados.getInsertedValue("Nombre") + "')";
            System.out.println(query);
            queries.execute(query);
            estados.reset();
        });
        colonias.getInsBtn().addActionListener(e -> {
            String query = "insert into Colonias values (" + colonias.getInsertedValue("ID Colonia") + ", " +
                    colonias.getInsertedValue("ID Estado") + "," + colonias.getInsertedValue("ID Ciudad") + ",'" +
                    colonias.getInsertedValue("Nombre") + "')";
            System.out.println(query);
            queries.execute(query);
            colonias.reset();
        });
        sucursales.getInsBtn().addActionListener(e -> {
            String query = "insert into Sucursales values (" + sucursales.getInsertedValue("ID Sucursal") + ", " +
                    sucursales.getInsertedValue("ID Estado") + "," + sucursales.getInsertedValue("ID Ciudad") + ",'" +
                    sucursales.getInsertedValue("Nombre") + "'," + sucursales.getInsertedValue("ID Colonia") +
                    ",'" + sucursales.getInsertedValue("Calle") + "')";
            System.out.println(query);
            queries.execute(query);
            sucursales.reset();
        });
        empleados.getInsBtn().addActionListener(e -> {
            String query = "insert into Empleados values (" + empleados.getInsertedValue("ID Empleado") + ", " +
                    empleados.getInsertedValue("ID Sucursal") + ",'" + empleados.getInsertedValue("Nombre") + "','" +
                    empleados.getInsertedValue("Apellido Paterno") + "','" + empleados.getInsertedValue("Apellido Materno") +
                    "'," + empleados.getInsertedValue("Teléfono") + ",'" + empleados.getInsertedValue("Correo") + "')";
            System.out.println(query);
            queries.execute(query);
            empleados.reset();
        });


        // Updates
        estadosAuto.getUpdBtn().addActionListener(e -> {
            String query = "update estadosAuto set descripcion = '" + estadosAuto.getInsertedValue("Descripción") +
                    "' where idEstadoAuto = " + estadosAuto.getInsertedValue("ID Estado");
            System.out.println(query);
            queries.execute(query);
            estadosAuto.reset();
        });
        marcas.getUpdBtn().addActionListener(e -> {
            String query = "update marcas set NombreMarca = '" + marcas.getInsertedValue("Nombre") + "' where IDMarca = " +
                    marcas.getInsertedValue("ID Marca");
            System.out.println(query);
            queries.execute(query);
            marcas.reset();
        });
        modelos.getUpdBtn().addActionListener(e -> {
            String query = "update modelos set NombreModelo = '" + modelos.getInsertedValue("Nombre") + "' where idModelo = "+
                    modelos.getInsertedValue("ID Modelo");
            System.out.println(query);
            queries.execute(query);
            modelos.reset();
        });
        autos.getUpdBtn().addActionListener(e -> {
            String query = "update autos set disponible = '" + autos.getInsertedValue("Disponible") + "' where Placa = '" +
                    autos.getInsertedValue("Placa") +"'";
            System.out.println(query);
            queries.execute(query);
            autos.reset();
        });
        estados.getUpdBtn().addActionListener(e -> {
            String query = "update estados set NombreEstado = '" + estados.getInsertedValue("Nombre") + "' where IDEstado = "+
                    estados.getInsertedValue("ID Estado");
            System.out.println(query);
            queries.execute(query);
            estados.reset();
        });
        ciudades.getUpdBtn().addActionListener(e -> {
            String query = "update ciudades set NombreCiudad = '" + ciudades.getInsertedValue("Nombre") + "' where IDCiudad = " +
                    ciudades.getInsertedValue("ID Ciudad") + " and idEstado = " + ciudades.getInsertedValue("ID Estado");
            System.out.println(query);
            queries.execute(query);
            ciudades.reset();
        });
        colonias.getUpdBtn().addActionListener(e -> {
            String query = "update colonias set nombreColonia = '" + colonias.getInsertedValue("Nombre") +
                    "' where idColonia = " + colonias.getInsertedValue("ID Colonia") + " and idCiudad = " +
                    colonias.getInsertedValue("ID Ciudad") + " and idEstado = " + colonias.getInsertedValue("ID Estado");
            System.out.println(query);
            queries.execute(query);
            colonias.reset();
        });
        logAdmin.getUpdBtn().addActionListener(e ->{
            String query = "update logAdministradores set contrasena = '" + logAdmin.getInsertedValue("Contraseña") +
                    "' where usuario = '" + logAdmin.getInsertedValue("Usuario") + "'";
            System.out.println(query);
            queries.execute(query);
            logAdmin.reset();
        });
        sucursales.getUpdBtn().addActionListener(e -> {
            String query = "update sucursales set nombreSucursal = '" + sucursales.getInsertedValue("Nombre") +
                    "' where idSucursal = " + sucursales.getInsertedValue("ID Sucursal");
            System.out.println(query);
            queries.execute(query);
            sucursales.reset();
        });
        empleados.getUpdBtn().addActionListener(e -> {
            String query = "update empleados set nombreEmp = '" + empleados.getInsertedValue("Nombre") +
                    "' where idEmpleado = " + empleados.getInsertedValue("ID Empleado");
            System.out.println(query);
            queries.execute(query);
            empleados.reset();
        });
        clientes.getUpdBtn().addActionListener(e -> {
            String query = "update clientes set nombreCli = '" + clientes.getInsertedValue("Nombre") +
                    "' where noLicencia = " + clientes.getInsertedValue("No. Licencia");
            System.out.println(query);
            queries.execute(query);
            clientes.reset();
        });
        rentas.getUpdBtn().addActionListener(e -> {
            String query = "update rentas set diasRenta = '" + rentas.getInsertedValue("Dias Renta") +
                    "' where idRenta = " + rentas.getInsertedValue("ID Renta");
            System.out.println(query);
            queries.execute(query);
            rentas.reset();
        });


        // Deletes
        estadosAuto.getDelBtn().addActionListener(e -> {
            String query = "delete from estadosAuto where idEstadoAuto = " + estadosAuto.getInsertedValue("ID Estado");
            System.out.println(query);
            queries.execute(query);
            estadosAuto.reset();
        });
        logAdmin.getDelBtn().addActionListener(e -> {
            String query = "delete from logAdministradores where usuario = '" +
                    logAdmin.getInsertedValue("Usuario") + "'";
            System.out.println(query);
            queries.execute(query);
            logAdmin.reset();
        });
        marcas.getDelBtn().addActionListener(e -> {
            String query = "delete from marcas where IDMarca = " + marcas.getInsertedValue("ID Marca");
            System.out.println(query);
            queries.execute(query);
            marcas.reset();
        });
        modelos.getDelBtn().addActionListener(e -> {
            String query = "delete from modelos where IDModelo = " + modelos.getInsertedValue("ID Modelo");
            System.out.println(query);
            queries.execute(query);
            modelos.reset();
        });
        autos.getDelBtn().addActionListener(e -> {
            String query = "delete from autos where Placa = '" + autos.getInsertedValue("Placa") +"'";
            System.out.println(query);
            queries.execute(query);
            autos.reset();
        });
        estados.getDelBtn().addActionListener(e -> {
            String query = "delete from estados where IDEstado= " + estados.getInsertedValue("ID Estado");
            System.out.println(query);
            queries.execute(query);
            estados.reset();
        });
        ciudades.getDelBtn().addActionListener(e -> {
            String query = "delete from ciudades where IDCiudad = " + ciudades.getInsertedValue("ID Ciudad") +
                    " and idEstado = " + ciudades.getInsertedValue("ID Estado");
            System.out.println(query);
            queries.execute(query);
            ciudades.reset();
        });
        colonias.getDelBtn().addActionListener(e -> {
            String query = "delete from colonias where idColonia = " + colonias.getInsertedValue("ID Colonia") +
                    " and idEstado = " + colonias.getInsertedValue("ID Estado") + " and idCiudad = " +
                    colonias.getInsertedValue("ID Ciudad");
            System.out.println(query);
            queries.execute(query);
            colonias.reset();
        });
        sucursales.getDelBtn().addActionListener(e -> {
            String query = "delete from sucursales where idSucursal = " +
                    sucursales.getInsertedValue("ID Sucursal");
            System.out.println(query);
            queries.execute(query);
            sucursales.reset();
        });
        empleados.getDelBtn().addActionListener(e -> {
            String query = "delete from empleados where idEmpleado = " +
                    empleados.getInsertedValue("ID Empleado");
            System.out.println(query);
            queries.execute(query);
            empleados.reset();
        });
        clientes.getDelBtn().addActionListener(e -> {
            String query = "delete from clientes where noLicencia = " +
                    clientes.getInsertedValue("No. Licencia");
            System.out.println(query);
            queries.execute(query);
            clientes.reset();
        });
        rentas.getDelBtn().addActionListener(e -> {
            String query = "delete from rentas where idRenta = " +
                    rentas.getInsertedValue("ID Renta");
            System.out.println(query);
            queries.execute(query);
            rentas.reset();
        });


        menu.addToCentral(login);
        menu.addToCentral(editPanel);
        menu.addToCentral(selectPanel);
        menu.setDefaultPanel(login);

        menu.getButton("Iniciar sesión").addActionListener(e -> menu.setDefaultPanel(login));
        menu.getButton("Tablas").addActionListener(e -> menu.setDefaultPanel(editPanel));
        menu.getButton("Consultar").addActionListener(e -> menu.setDefaultPanel(selectPanel));

        menu.getButton("Tablas").setEnabled(false);
        menu.getButton("Consultar").setEnabled(false);

        login.getButton().addActionListener(e -> {
            if (!login.isEmpty()) {
                try {
                    boolean found = false;
                    ResultSet rs = queries.execute("select * from logAdministradores");
                    while (rs.next()) {
                        if (login.getUser().equals(rs.getString("usuario"))
                        && login.getPassword().equals(rs.getString("contrasena"))) {
                            menu.getButton("Tablas").setEnabled(true);
                            menu.getButton("Consultar").setEnabled(true);
                            found = true;
                        }
                    }
                    rs.close();
                    if (!found) JOptionPane.showMessageDialog(window, "No se encontró el usuario");
                } catch (SQLException exception) {
                    JOptionPane.showMessageDialog(window, exception.getMessage());
                }
                login.clean();
            } else {
                JOptionPane.showMessageDialog(window, "Inserta todos los datos");
            }
        });

        window.setMainPanel(menu);
        window.setSize(490, 310);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
