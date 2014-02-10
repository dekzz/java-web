    public String provjeraKorisnika() {
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        BP_Konfiguracija bp = (BP_Konfiguracija) sc.getAttribute("BP_Konfiguracija");
        if (bp == null) {
            return "ERROR";
        }
        if (korisnickoIme == null || korisnickoIme.length() == 0 || korisnickaLozinka == null || korisnickaLozinka.length() == 0) {
            return "NOT_OK";
        }

        String connUrl = bp.getServer_database() + bp.getUser_database();
        String sql = "SELECT ime, prezime, lozinka, email_adresa, vrsta FROM polaznici WHERE kor_ime = '" + korisnickoIme + "'";
        try {
            Class.forName(bp.getDriver_database());
        } catch (ClassNotFoundException ex) {
            return "ERROR";
        }

        try (
                Connection conn = DriverManager.getConnection(connUrl, bp.getUser_username(), bp.getUser_password());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {

            if (!rs.next()) {
                return "NOT_OK";
            }

            String ime = rs.getString("ime");
            String prezime = rs.getString("prezime");
            int vrsta = rs.getInt("vrsta");

            Korisnik korisnik = new Korisnik(korisnickoIme, prezime, ime, "", vrsta);

            HttpSession sesija = (HttpSession) context.getExternalContext().getSession(true);
            sesija.setAttribute("korisnik", korisnik);
            return "OK";

        } catch (SQLException ex) {
            return "ERROR";
        }
}
