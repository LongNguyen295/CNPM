package com.example.citizenmanagement.models;


import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Dialog;


import java.security.PublicKey;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection() {
        String dbName = "QUANLYDANCU";
        String dbUser = "group11";
        String dbPassword = "group11";

        String url = "jdbc:sqlserver://LAPTOP-10MBD6CH\\dbo:1433;databaseName=" + dbName +
                ";encrypt=true;integratedSecurity=false;trustServerCertificate=true";

//        String dbUser = "sa";
//        String dbPassword = "123";
//        String url = "jdbc:sqlserver://DESKTOP-SM2FAUO:1433;databaseName=" + dbName +
//                ";encrypt=true;integratedSecurity=false;trustServerCertificate=true";


        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (Exception e) {
            System.out.println("loi o dayyyy");
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private ResultSet executeQuery(String query) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    private void executeUpdate(String query) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /******************************************************************************************/
    // Citizen Manager Section - Phần Đăng Nhập
    public ResultSet getCitizenManagerData(String tenDangNhap, String matKhau) {

        String query = "SELECT * FROM NGUOIQUANLY\n" +
                "WHERE TENDANGNHAP = '" + tenDangNhap + "' AND MATKHAU = '" + matKhau +"'";
        return executeQuery(query);
    }

    public ResultSet checkCitizenManagerUsernameExisted(String tenDangNhap) {
        String query = "SELECT * FROM NGUOIQUANLY\n" +
                "WHERE TENDANGNHAP = '" + tenDangNhap + "'";
        return executeQuery(query);
    }

    public ResultSet checkCitizenManagerAccountExisted(String hoTen, String tenDangNhap, String soDienThoai, int vaiTro) {
        String query = "SELECT * FROM NGUOIQUANLY\n" +
                "WHERE HOTEN = N'" + hoTen + "' AND TENDANGNHAP = '" + tenDangNhap + "' AND SODIENTHOAI = '" + soDienThoai + "' AND VAITRO = '" + vaiTro + "'";
        return executeQuery(query);
    }
    public void updateCitizenManagerAccountPassword(String hoTen, String tenDangNhap, String soDienThoai, int vaiTro, String maKhau) {
        String query = "UPDATE NGUOIQUANLY SET MATKHAU = '" + maKhau + "' \n" +
                "WHERE HOTEN = N'" + hoTen+ "' AND TENDANGNHAP = '" + tenDangNhap + "' AND SODIENTHOAI = '" + soDienThoai + "' AND VAITRO = '" + vaiTro + "'";
        executeUpdate(query);
    }
    public void setCitizenManagerData(String hoTen, String tenDangNhap, String matKhau, String soDienThoai, int vaiTro) {
        String query = "INSERT INTO NGUOIQUANLY(HOTEN, TENDANGNHAP, MATKHAU, SODIENTHOAI, VAITRO)\n" +
                        "VALUES (N'" + hoTen + "', '" + tenDangNhap + "', '" + matKhau + "', '" + soDienThoai + "', "+ Integer.toString(vaiTro)+ ")";

        executeUpdate(query);
    }

    /**************************************************************************************/
    // trang chủ - thống kê quản lý dân cư
    public ResultSet getNumberOfTamTru(int nam){
        String query = "SELECT COUNT(MAGIAYTAMTRU) FROM TAMTRU " +
                "WHERE " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY)";
        return executeQuery(query);
    }
    public ResultSet getNumberOfTamVang(int nam){

        String query = "SELECT COUNT(MAGIAYTAMVANG) FROM TAMVANG WHERE " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY)";
        return executeQuery(query);
    }
    public ResultSet getNumberOfNhanhKhau() {
        String query = "select count(MANHANKHAU) from NHANKHAU";
        return executeQuery(query);
    }

    public ResultSet getNumberOfHoKhau(){
        String query = "select count(MAHOKHAU) from HOKHAU";
        return executeQuery(query);
    }

    public ResultSet getNumberOfTamTru(){
        String query = "SELECT COUNT(MAGIAYTAMTRU) FROM TAMTRU WHERE YEAR(GETDATE()) BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY) ";
        return executeQuery(query);
    }
    public ResultSet getNumberOfTamVang(){
        String query = "SELECT COUNT(MAGIAYTAMVANG) FROM TAMVANG WHERE YEAR(GETDATE()) BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY) ";
        return executeQuery(query);
    }

    public ResultSet getNumberOfNhanKhauNam(){
        String query = "select count(MANHANKHAU) from NHANKHAU where GIOITINH = 1";
        return executeQuery(query);
    }
    public ResultSet getNumberOfNhanKhauNu(){
        String query = "select count(MANHANKHAU) from NHANKHAU where GIOITINH = 0";
        return executeQuery(query);
    }

    public ResultSet getNumberOfNhanKhauDuoi3Tuoi(){
        String query = "select count(MANHANKHAU) \n" +
                "from NHANKHAU\n" +
                "where YEAR(GETDATE()) - YEAR(NGAYSINH) < 3 AND YEAR(GETDATE()) - YEAR(NGAYSINH) >= 0";
        return executeQuery(query);
    }
    public ResultSet getNumberOfNhanKhauTu3Den10Tuoi(){
        String query = "select count(MANHANKHAU) \n" +
                "from NHANKHAU\n" +
                "where YEAR(GETDATE()) - YEAR(NGAYSINH) >= 3 AND YEAR(GETDATE()) - YEAR(NGAYSINH) < 10";
        return executeQuery(query);
    }

    public ResultSet getNumberOfNhanKhauTu10Den18Tuoi(){
        String query = "select count(MANHANKHAU) \n" +
                "from NHANKHAU\n" +
                "where YEAR(GETDATE()) - YEAR(NGAYSINH) >= 10 AND YEAR(GETDATE()) - YEAR(NGAYSINH) < 18";
        return executeQuery(query);
    }

    public ResultSet getNumberOfNhanKhauTu18Den60Tuoi(){
        String query = "select count(MANHANKHAU) \n" +
                "from NHANKHAU\n" +
                "where YEAR(GETDATE()) - YEAR(NGAYSINH) >= 18 AND YEAR(GETDATE()) - YEAR(NGAYSINH) < 60";
        return executeQuery(query);
    }
    public ResultSet getNumberOfNhanKhauTren60Tuoi(){
        String query = "select count(MANHANKHAU) \n" +
                "from NHANKHAU\n" +
                "where YEAR(GETDATE()) - YEAR(NGAYSINH) >= 60";
        return executeQuery(query);
    }
    public ResultSet getNamHienTai(){
        String query = "select YEAR(GETDATE())";
        return executeQuery(query);
    }
    public ResultSet getTongDotThu(){
        String query = "select distinct MADOTTHU from DOTTHUPHI";
        return executeQuery(query);
    }
    public ResultSet getFeeUngHoByDot(int makhoanthu){
        String query = "select sum(SOTIENDADONG) from DONGGOP WHERE MAKHOANTHU = "+makhoanthu;
        return executeQuery(query);
    }
    public ResultSet getFeeChungCuByDot(int maDotthu){
        String query = "select sum(SOTIENDADONG) from DANHSACHTHUPHI WHERE MADOTTHU ="+maDotthu;
        return executeQuery(query);
    }
    public ResultSet getFeeTenDotThu(int maDotthu){
        String query = "select TEN from DOTTHUPHI WHERE MADOTTHU ="+maDotthu;
        return executeQuery(query);
    }
    public ResultSet getSoLuongLoaiPhi(int maDotthu){
        String query = "select COUNT(ID) from LOAIPHI WHERE MAKHOANTHU ="+maDotthu;
        return executeQuery(query);
    }
    public ResultSet getTongTienDongGop(int maDotthu){
        String query = "SELECT SUM(SOTIENDADONG) FROM DONGGOP WHERE TRANGTHAI =1 AND MAKHOANTHU ="+maDotthu;
        return executeQuery(query);
    }
    public ResultSet getTongTienDongGop(){
        String query = "SELECT SUM(SOTIENDADONG) FROM DONGGOP WHERE TRANGTHAI =1";
        return executeQuery(query);
    }
    public ResultSet getSumAllFee(){
        String query = "SELECT SUM(DS.SOTIENDADONG) \n" +
                "FROM DANHSACHTHUPHI DS\n";
        return executeQuery(query);
    }
    public ResultSet getHoKhauOfNamHienTai(){
        String query = "SELECT COUNT(MAHOKHAU)\n" +
                "FROM HOKHAU";
        return executeQuery(query);
    }

    public ResultSet getHoKhauOfNam(int nam){
        String query = "SELECT COUNT(MAHOKHAU)\n" +
                "FROM HOKHAU\n" +
                "WHERE " + nam + " > YEAR(NGAYTAO)";
        return executeQuery(query);
    }

    public ResultSet getTamTruOfThangVaNam(int thang,int nam){
        String query = "SELECT COUNT(MAGIAYTAMTRU)\n" +
                "FROM TAMTRU\n" +
                "WHERE " + thang + " BETWEEN MONTH(TUNGAY) AND MONTH(DENNGAY)" + "\n" +
                "AND " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY)";
        return executeQuery(query);
    }

    public ResultSet getTamTruViLyDoHocTap(int nam){
        String query = "SELECT COUNT(MAGIAYTAMTRU)\n" +
                "FROM TAMTRU\n" +
                "WHERE LYDO LIKE N'%Học tập%' AND " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY)";
       return executeQuery(query);
    }
    public ResultSet getTamTruViLyDoLamViec(int nam){
        String query = "SELECT COUNT(MAGIAYTAMTRU)\n" +
                "FROM TAMTRU\n" +
                "WHERE LYDO LIKE N'%Làm việc%' AND " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY)";
        return executeQuery(query);
    }

    public ResultSet getTamTruViLyDoSucKhoe(int nam){
        String query = "SELECT COUNT(MAGIAYTAMTRU)\n" +
                "FROM TAMTRU\n" +
                "WHERE LYDO LIKE N'%sức khỏe%' AND " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY)";
        return executeQuery(query);
    }

    public ResultSet getTamVangOfThangVaNam(int thang,int nam){
        String query = "SELECT COUNT(MAGIAYTAMVANG)\n" +
                "FROM TAMVANG\n" +
                "WHERE (YEAR(TUNGAY) = " + nam + " AND MONTH(TUNGAY) <= " + thang + ") OR (YEAR(DENNGAY) = " + nam + " AND MONTH(DENNGAY) >= " + thang + ")\n" +
                "\tOR (YEAR(TUNGAY) < " + nam + " AND YEAR(DENNGAY) > " + nam + ")";

        return executeQuery(query);
    }

    public ResultSet getTamVangViLyDoHocTap(int nam){
        String query = "SELECT COUNT(MAGIAYTAMVANG) FROM TAMVANG WHERE LYDO LIKE N'%Học tập%' AND " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY)";

        return executeQuery(query);
    }

    public ResultSet getTamVangViLyDoLamViec(int nam){
        String query = "SELECT COUNT(MAGIAYTAMVANG) FROM TAMVANG   WHERE LYDO LIKE N'%Làm việc%' AND " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY)";
        return executeQuery(query);
    }

    public ResultSet getTamVangViLyDoSucKhoe(int nam){
        String query = "SELECT COUNT(MAGIAYTAMVANG) FROM TAMVANG  WHERE LYDO LIKE N'%sức Khoẻ%' AND " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY)";
        return executeQuery(query);
    }



    //Nhân khẩu
    public int addNhanKhau (String hoTen, String CCCD, String ngaySinh, int gioiTinh, String noiSinh, String nguyenQuan,String danToc, String tonGiao, String quocTich, String noiThuongTru, String ngheNghiep, String ghiChu ){
        int thanhcong = 0;
        String querry = "insert into NHANKHAU (HOTEN, SOCANCUOC, NGAYSINH, GIOITINH, NOISINH, NGUYENQUAN, DANTOC, TONGIAO, QUOCTICH, NOITHUONGTRU, NGHENGHIEP, NGAYTAO, GHICHU ) " +
                " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement pre = connection.prepareStatement(querry);
            pre.setNString(1,hoTen); pre.setString(2,CCCD);
            pre.setString(3,ngaySinh); pre.setInt(4,gioiTinh);
            pre.setNString(5,noiSinh); pre.setNString(6,nguyenQuan);
            pre.setNString(7,danToc); pre.setNString(8,tonGiao);
            pre.setNString(9,quocTich);
            pre.setNString(10,noiThuongTru); pre.setNString(11,ngheNghiep);
            pre.setDate(12, Date.valueOf(LocalDate.now().toString())); pre.setNString(13,ghiChu);
            thanhcong = pre.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println("Lỗi thêm nhân khẩu");
            throw new RuntimeException(e);
        }
        return thanhcong;
    }

    public int addTamtru(String hoTen, String CCCD, String ngaySinh, int gioiTinh, String noiSinh, String nguyenQuan, String danToc, String tonGiao, String quocTich, String noiThuongTru, String ngheNghiep, String sdt, Date ngayDen, Date ngayDi, String liDo ) {
        int thanhcong = 0;
        String que = "EXEC INSERT_TAMTRU ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        try{
            PreparedStatement pre = connection.prepareStatement(que);
            pre.setNString(1,hoTen); pre.setString(2,CCCD);
            pre.setString(3, ngaySinh); pre.setInt(4,gioiTinh);
            pre.setNString(5,noiSinh); pre.setNString(6,nguyenQuan);
            pre.setNString(7,danToc); pre.setNString(8,tonGiao);
            pre.setNString(9,quocTich);
            pre.setNString(10,noiThuongTru); pre.setNString(11,ngheNghiep);
            pre.setString(12, sdt);
            pre.setDate(13, ngayDen); pre.setDate(14,ngayDi);
            pre.setNString(15,liDo);
            thanhcong = pre.executeUpdate();
        }catch(Exception e) {
            System.out.println("Lỗi thêm nhân khẩu");
            throw new RuntimeException(e);
        }

        return thanhcong;
    }

    public int addKhaitu(String maNguoiKhai, String maNguoiMat, Date ngayMat, String liDo) {
        int thanhcong = 0;
        String que = "INSERT INTO KHAITU (MANHANKHAUNGUOIKHAI, MANHANKHAUNGUOICHET, NGAYKHAI, NGAYCHET, LYDOCHET) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(que);
            pre.setString(1, maNguoiKhai);
            pre.setString(2, maNguoiMat);
            pre.setDate(3, Date.valueOf(LocalDate.now()));
            pre.setDate(4, ngayMat);
            pre.setNString(5, liDo);
            thanhcong = pre.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi khai tử");
            throw new RuntimeException(e);
        }
        return thanhcong;
    }

    public int capnhatNhanKhau (String string){
        int thanhcong = 0;
        String querry = "update NHANKHAU SET NGAYTAO = ? Where SOCANCUOC = ?";
        try{
            PreparedStatement pre = connection.prepareStatement(querry);
            pre.setDate(1,Date.valueOf(LocalDate.now().toString()));
            pre.setString(2,string);
            thanhcong = pre.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println("Lỗi câpj nhật khẩu");
            throw new RuntimeException(e);
        }
        return thanhcong;
    }

    public int capnhatNhanKhauShow (String hoten, Date ngaysinh, int Gioitinh, String noisinh, String nguyenquan, String dantoc, String tongiao, String quoctich, String noithuongtru, String nghenghiep, String ghichu,String manhankhau){
        int thanhcong = 0;
        String querry = "update NHANKHAU SET HOTEN = ? , NGAYSINH = ? , GIOITINH = ? , NOISINH = ? , NGUYENQUAN =? , DANTOC =? , TONGIAO = ? , QUOCTICH =? , NOITHUONGTRU = ? , NGHENGHIEP = ?, GHICHU =? Where MANHANKHAU = ?";
        try{
            PreparedStatement pre = connection.prepareStatement(querry);
            pre.setNString(1,hoten);
            pre.setDate(2, ngaysinh);
            pre.setInt(3,Gioitinh);
            pre.setNString(4,noisinh);
            pre.setNString(5,nguyenquan);
            pre.setNString(6,dantoc);
            pre.setNString(7,tongiao);
            pre.setNString(8,quoctich);
            pre.setNString(9,noithuongtru);
            pre.setNString(10,nghenghiep);
            pre.setNString(11,ghichu);
            pre.setString(12,manhankhau);
            thanhcong = pre.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println("Lỗi câpj nhật khẩu");
            throw new RuntimeException(e);
        }
        return thanhcong;
    }
    // Nhân khâur
    public ResultSet KiemTraXemMaNhanKhauDaTonTaiTrongTamVang(int manhankhau){

        ResultSet resultSet = null;
        String query = "SELECT COUNT(MANHANKHAU) FROM TAMVANG WHERE MANHANKHAU =" + manhankhau;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;

    }
    public void dangKiTamVang(int maNhanKhau, String noiTamTru,String tuNgay, String denNgay,String lyDo){
        String dangkitamvang = "EXEC INSERT_TAM_VANG ?, ?, ?, ?,?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(dangkitamvang);
            preparedStatement.setInt(1,maNhanKhau);

            preparedStatement.setString(3,tuNgay);

            preparedStatement.setString(4,denNgay);

            preparedStatement.setNString(5,lyDo);

            if(noiTamTru.isEmpty())
                preparedStatement.setNString(2,null);
            else
                preparedStatement.setNString(2,noiTamTru);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet KiemTraMaNhanKhauCoTonTaiHayKhong(int manhankhau){
        ResultSet resultSet = null;
        String query = "SELECT COUNT(MANHANKHAU) FROM NHANKHAU WHERE MANHANKHAU =" + manhankhau;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet truyvanlistNhanKhau( String manhankhau) {
        ResultSet resultSet = null;
        String que = "SELECT HOTEN, SOCANCUOC, NGAYSINH, GIOITINH, NOISINH, NGUYENQUAN, DANTOC, TONGIAO, QUOCTICH, NOITHUONGTRU, NGHENGHIEP, NGAYTAO, GHICHU FROM NHANKHAU WHERE MANHANKHAU = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(que);
            preparedStatement.setString(1,    manhankhau );
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public int xoa_tam_tru(String MaNhanKhau) {
        if(!MaNhanKhau.isEmpty()) {
            String query = "Delete TAMTRU where MANHANKHAU = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, MaNhanKhau);
                statement.executeUpdate();
                return 1;
            } catch (Exception e) {
                return 0;
            }
        }
        else
            return 0;
    }

    public ResultSet nhanKhau_timkiem(String string) {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU where MANHANKHAU like ? or SOCANCUOC like ? or HOTEN like ?";
        try {
            PreparedStatement preparedstatement = connection.prepareStatement(querry);
            preparedstatement.setString(1, "%" + string + "%");
            preparedstatement.setString(2, "%" + string + "%");
            preparedstatement.setNString(3, "%" + string + "%");
            resultSet = preparedstatement.executeQuery();
        }
        catch(Exception e) {
            System.out.println("Lỗi tìm kiếm");
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    public ResultSet truyvan() {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU;";
        try{
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(querry);
        }
        catch(Exception e) {

        }
        return resultSet;
    }

    public ResultSet truyvanTamTru() {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU where GHICHU like N'%tạm trú%';";
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(querry);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    /***********************************************************************************/
    // Hộ khẩu

    public int addHoKhau(String ma_ch, String diachi, String ghichu){
        if(!ma_ch.isEmpty() && !diachi.isEmpty()) {
            String query = "EXEC INSERT_HOKHAU ?, ?, ?, ?";
            try {
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setString(1, ma_ch);
                statement.setNString(2, diachi);
                statement.setString(3,LocalDate.now().toString());
                if(ghichu.isEmpty())
                    statement.setNString(4,null);
                else
                    statement.setNString(4, ghichu);

                statement.executeUpdate();
                return 1;
            } catch (Exception e) {
                System.out.println("loi o addHoKhau");
//                throw new RuntimeException(e);
                return 0;
            }
        }
        else
            return 0;
    }
    public ResultSet getDanhSachHoKhau(){
        String query = "select * from HOKHAU";
        return executeQuery(query);
    }
    public ResultSet timKiem(String dieukien){
        ResultSet resultSet=null;
        String query = "SELECT * FROM HOKHAU\n" +
                "WHERE MAHOKHAU LIKE ? OR TENCHUHO LIKE ? OR DIACHI LIKE ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + dieukien + "%");
            statement.setNString(2, "%" + dieukien + "%");
            statement.setNString(3, "%" + dieukien + "%");
            resultSet = statement.executeQuery();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    public ResultSet lay_ho_khau(String ma_chu_ho){
        String query = "select * from HOKHAU WHERE IDCHUHO = " + ma_chu_ho;
        return executeQuery(query);
    }
    public ResultSet getMaHoKhau(String maChuHo) {
        String query = "Select mahokhau from thanhviencuaho where manhankhau = " + maChuHo;

        return executeQuery(query);
    }
    public int capNhatHoKhau(String idHoKhau, String maChuHo, String diaChi, String ghiChu, String xeMay, String oTo){

        try {
            String capnhat = "update HOKHAU set IDCHUHO=?, DIACHI=?, GHICHU=?, tenchuho=?, XEMAY=?, OTO=? where MAHOKHAU=?";
            PreparedStatement preparedStatement = connection.prepareStatement(capnhat);
            preparedStatement.setString(1,maChuHo);
            preparedStatement.setNString(2,diaChi);
            if(ghiChu.isEmpty())
                preparedStatement.setNString(3,null);
            else
                preparedStatement.setNString(3, ghiChu);
            preparedStatement.setNString(5, xeMay);
            preparedStatement.setNString(6, oTo);
            preparedStatement.setString(7,idHoKhau);


            String lay_ten_chu="select * from nhankhau where manhankhau="+maChuHo;
            Statement statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery(lay_ten_chu);
            if(resultSet1.isBeforeFirst()){
                resultSet1.next();
                preparedStatement.setString(4,resultSet1.getNString(2));
            }
            else {
                preparedStatement.setString(4,null);
            }
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    public int xoaHoKhau(String maHoKhau) {
        String query = "DELETE from HOKHAU\n" +
                "WHERE MAHOKHAU = " + maHoKhau;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return  1;
        } catch (SQLException e) {
            return 0;
            //throw new RuntimeException(e);
        }
    }
    public ResultSet getDanhSachTamVang() {
        ResultSet resultSet = null;
        String query= "SELECT * FROM TAMVANG TV , NHANKHAU NK WHERE TV.MANHANKHAU = NK.MANHANKHAU";
        Statement statement;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet lay_cac_thanh_vien(String ma_ho){
        String query = "select * from THANHVIENCUAHO where MAHOKHAU="+ma_ho;
        ResultSet resultSet=null;
        try{
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        }catch (Exception e){
            System.out.println("loi o truy van thanh vien");
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet lay_nhan_khau(String ma_nhan_khau) {
        String query = " select SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU where MANHANKHAU = " + ma_nhan_khau;
        return executeQuery(query);
    }

    public void add_thanh_vien_cua_ho(String maNhanKhau,String ma_ho, String quan_he){
        String query = "INSERT INTO THANHVIENCUAHO VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,maNhanKhau);
            preparedStatement.setString(2,ma_ho);
            preparedStatement.setNString(3,quan_he);
            preparedStatement.executeUpdate();

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public void xoa_thanh_vien_cua_ho(String maNhanKhau){
        String query1 = "select * FROM NHANKHAU WHERE MANHANKHAU = " + maNhanKhau;
        try{
            Statement statement1 = connection.createStatement();
            ResultSet resultSet=statement1.executeQuery(query1);
            if(resultSet.isBeforeFirst()) {
                resultSet.next();
                String query = "DELETE FROM THANHVIENCUAHO WHERE MANHANKHAU= "+resultSet.getString(1);
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
            }
        }catch (Exception e){
            System.out.println("loi o xoa_thanh_vien_cua_ho");
            e.printStackTrace();
        }
    }
    public void xoaThanhVienCuaHo(String maNhanKhau) {
        String query = "DELETE FROM THANHVIENCUAHO WHERE MANHANKHAU = " + maNhanKhau;
        System.out.println("da xoa " + maNhanKhau);
        executeUpdate(query);
    }

    public ResultSet truyvan_chua_co_nha() {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU WHERE MANHANKHAU NOT IN (SELECT MANHANKHAU FROM THANHVIENCUAHO);";
        try{
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(querry);
        }
        catch(Exception e) {
            System.out.println("loi truy van chua co ho khau");
        }
        return resultSet;
    }

    public ResultSet nhanKhau_timkiem_chua_co_nha(String string) {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU where (MANHANKHAU like ? or SOCANCUOC like ? or HOTEN like ?) AND MANHANKHAU NOT IN (SELECT MANHANKHAU FROM THANHVIENCUAHO);";
        try {
            PreparedStatement preparedstatement = connection.prepareStatement(querry);
            preparedstatement.setString(1, "%" + string + "%");
            preparedstatement.setString(2, "%" + string + "%");
            preparedstatement.setNString(3, "%" + string + "%");
            resultSet = preparedstatement.executeQuery();
        }
        catch(Exception e) {
            System.out.println("Lỗi tìm kiếm");
            throw new RuntimeException(e);
        }
        return resultSet;
    }

   public String lay_chu_ho(String ma_ho_khau){
        String query = "select * from HOKHAU WHERE MAHOKHAU="+ma_ho_khau;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);

                if (resultSet.isBeforeFirst()) {
                    resultSet.next();
                    return resultSet.getString(2);
                }
        }catch (Exception e){
            System.out.println("loi o lay_ho_khau");
            return null;
        }
        return null;
   }

    /***************************************************************************/
    // Quản lý thu phí
    public ResultSet getDanhSachDongPhi() {
        String query = "SELECT HK.MAHOKHAU, NK.HOTEN, HK.DIACHI, COUNT(TV.MANHANKHAU)\n" +
                "FROM HOKHAU HK INNER JOIN NHANKHAU NK ON HK.IDCHUHO = NK.MANHANKHAU\n" +
                "\tINNER JOIN THANHVIENCUAHO TV ON HK.MAHOKHAU = TV.MAHOKHAU\n" +
                "GROUP BY HK.MAHOKHAU, NK.HOTEN, HK.DIACHI ORDER BY HK.MAHOKHAU";
        return executeQuery(query);
    }
    public FeeHoaDon getFeeHoaDonSummary() throws SQLException {
        String query =
                "SELECT SUM(TIENNHA) AS [TIENNHA], " +
                        "SUM(TIENDICHVU) AS [TIENQUANLY], " +
                        "SUM(TIENXEMAY) AS [TIENXEMAY], " +
                        "SUM(TIENOTO) AS [TIENOTO], " +
                        "SUM(TIENDIEN) AS [TIENDIEN], " +
                        "SUM(TIENNUOC) AS [TIENNUOC], " +
                        "SUM(TIENINTERNET) AS [TIENINTERNET], " +
                        "SUM(DS.SOTIENDADONG) AS [SOTIENDADONG] " +
                        "FROM DANHSACHTHUPHI DS;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new FeeHoaDon(
                            rs.getInt("TIENNHA"),
                            rs.getInt("TIENQUANLY"),
                            rs.getInt("TIENDIEN"),
                            rs.getInt("TIENNUOC"),
                            rs.getInt("TIENINTERNET"),
                            rs.getInt("TIENXEMAY"),
                            rs.getInt("TIENOTO"),
                            rs.getString("SOTIENDADONG") // Tổng số tiền được trả dưới dạng chuỗi
                    );
                } else {
                    return null; // Không tìm thấy dữ liệu
                }
            }
        }
    }
    public FeeHoaDon getFeeHoaDonSummaryByDotThu(int madotthu) throws SQLException {
        String query =
                "SELECT SUM(TIENNHA) AS [TIENNHA], " +
                        "SUM(TIENDICHVU) AS [TIENQUANLY], " +
                        "SUM(TIENXEMAY) AS [TIENXEMAY], " +
                        "SUM(TIENOTO) AS [TIENOTO], " +
                        "SUM(TIENDIEN) AS [TIENDIEN], " +
                        "SUM(TIENNUOC) AS [TIENNUOC], " +
                        "SUM(TIENINTERNET) AS [TIENINTERNET], " +
                        "SUM(DS.SOTIENDADONG) AS [SOTIENDADONG] " +
                        "FROM DANHSACHTHUPHI DS WHERE MADOTTHU = "+madotthu;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new FeeHoaDon(
                            rs.getInt("TIENNHA"),
                            rs.getInt("TIENQUANLY"),
                            rs.getInt("TIENDIEN"),
                            rs.getInt("TIENNUOC"),
                            rs.getInt("TIENINTERNET"),
                            rs.getInt("TIENXEMAY"),
                            rs.getInt("TIENOTO"),
                            rs.getString("SOTIENDADONG") // Tổng số tiền được trả dưới dạng chuỗi
                    );
                } else {
                    return null; // Không tìm thấy dữ liệu
                }
            }
        }
    }
    public FeeHoaDon getFeeHoaDonDongGopSummary() throws SQLException {
        String query =
                "SELECT COUNT(DISTINCT MAKHOANTHU) AS [TONGLOAIPHI], " +
                        "SUM(SOTIENDADONG) AS [TONGTIEN] " +
                        "FROM DONGGOP;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new FeeHoaDon(
                            rs.getInt("TONGTIEN"),
                            rs.getInt("TONGLOAIPHI")
                    );
                } else {
                    return null; // Không tìm thấy dữ liệu
                }
            }
        }
    }

    public ResultSet danhsachdongphi_timKiem(String condition) {
        String query = "SELECT HK.MAHOKHAU\n" +
                "FROM HOKHAU HK INNER JOIN NHANKHAU NK ON HK.IDCHUHO = NK.MANHANKHAU\n" +
                "INNER JOIN THANHVIENCUAHO TV ON HK.MAHOKHAU = TV.MAHOKHAU\n" +
                "WHERE HK.MAHOKHAU LIKE '%" + condition + "%' \n" +
                "\tOR NK.HOTEN LIKE N'%" + condition + "%'\n" +
                "\tOR DIACHI LIKE N'%" + condition+ "%'\n" +
                "GROUP BY HK.MAHOKHAU, NK.HOTEN, HK.DIACHI";
        return executeQuery(query);
    }
    public void themKhoanThuPhi(int maKhoanThu,String tenKhoanThu, int batBuoc, long soTienCanDong, LocalDate ngayTao, String moTa) {
        String query = "INSERT INTO LOAIPHI(MAKHOANTHU,TEN, BATBUOC, SOTIENTRENMOTNGUOI, NGAYTAO, MOTA)\n" +
                "VALUES ("+maKhoanThu+ ",N'" + tenKhoanThu + "', " + batBuoc + ", "+ soTienCanDong + ", '" + ngayTao.toString() + "', N'" + moTa +"')";
        executeUpdate(query);
    }
    public void themKhoanThuPhiDot(int maDotThu, String tenDotThu, int batBuoc, LocalDate ngayTao, String moTa){
        String query = "INSERT INTO DOTTHUPHI(MADOTTHU,TEN, BATBUOC,NGAYTAO, MOTA)\n" +
                "VALUES ("+maDotThu+ ",N'" + tenDotThu + "', " + batBuoc + ", '" + ngayTao.toString() + "', N'" + moTa +"')";
        executeUpdate(query);
    }
    public boolean checkMaKhoanThu(int maKhoanThu){
        String query = "SELECT 1 FROM LOAIPHI WHERE MAKHOANTHU = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, maKhoanThu);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Nếu rs.next() trả về true, tức là có bản ghi thỏa mãn điều kiện
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra, trả về false
        }
    }
//    public int layMaKhoanThu(String tenKhoanThu,int batBuoc, long soTienCanDong, LocalDate ngayTao, String moTa) {
//        int maKhoanThu = -1;
//
//        String query = "SELECT MAKHOANTHU\n" +
//                "FROM LOAIPHI\n" +
//                "WHERE TEN = N'" + tenKhoanThu + "' AND BATBUOC = " + batBuoc +
//                " AND SOTIENTRENMOTNGUOI = " + soTienCanDong + " AND NGAYTAO = '" + ngayTao.toString() + "' AND MOTA = N'" + moTa +"'";
//        Statement statement;
//        ResultSet resultSet = null;
//        try {
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(query);
//            if (resultSet.isBeforeFirst()){
//                resultSet.next();
//                maKhoanThu = resultSet.getInt(1);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return maKhoanThu;
//    }
    public ResultSet getFeeCoDinh(int maHoKhau){
        String query =
                "SELECT \n" +
                        "\tGIATIENLOAICANHO, -- TIEN NHA\n" +
                        "\tFEEQLCHUNGCU, -- TIEN DICH VU QUAN LY\n" +
                        "\tPHIXEDAP*(CONVERT(INT,HK.XEMAY)) AS[TIENXEDAP], -- TIEN XE DAP\n" +
                        "\tPHIXEOTO*(CONVERT(INT,HK.OTO)) AS[TIENXEOTO]\t-- TIEN XE OTO\n" +
                        "FROM PHICODINH P JOIN HOKHAU HK ON P.LOAICANHO = CONVERT(INT,SUBSTRING(HK.DIACHI,1,4))\n" +
                        "WHERE MAHOKHAU = "+maHoKhau;
        return executeQuery(query);
    }
//    public ResultSet getFeeCoDinh_ThuHo(int maHoKhau, int maDotThu) throws SQLException {
//        String query =
//                "SELECT TENCHUHO, \n" +
//                        "P.GIATIENLOAICANHO,P.FEEQLCHUNGCU, \n" +
//                        "P.PHIXEDAP*(CONVERT(INT,HK.XEMAY)) AS[TIENXEMAY],\n" +
//                        "P.PHIXEOTO*(CONVERT(INT,HK.OTO)) AS[TIENOTO],\n" +
//                        "THANHTIENDIEN, TONGSODIEN,\n" +
//                        "THANHTIENNUOC, TONGSONUOC,\n" +
//                        "THANHTIENINTERNET\n" +
//                        "FROM FEETHUHO FH JOIN HOKHAU HK ON FH.IDCANHO = CONVERT(INT,SUBSTRING(HK.DIACHI,1,4))\n" +
//                        "\t\tJOIN PHICODINH P ON P.LOAICANHO = FH.IDCANHO\n" +
//                        "WHERE MADOTTHU = ? AND HK.MAHOKHAU = ?;";
//
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setInt(1, maDotThu); // Set the MADOTTHU parameter
//            stmt.setInt(2, maHoKhau); // Set the MAHOKHAU parameter
//
//            return stmt.executeQuery(); // Execute the query
//        }
//    }
public DanhSachThuPhiModel getFeeCoDinh_ThuHo(int maHoKhau, int maDotThu) throws SQLException {
    String query =
            "SELECT TENCHUHO, \n" +
                    "P.GIATIENLOAICANHO, P.FEEQLCHUNGCU, \n" +
                    "P.PHIXEDAP*(CONVERT(INT,HK.XEMAY)) AS [TIENXEMAY],\n" +
                    "P.PHIXEOTO*(CONVERT(INT,HK.OTO)) AS [TIENOTO],\n" +
                    "THANHTIENDIEN, TONGSODIEN,\n" +
                    "THANHTIENNUOC, TONGSONUOC,\n" +
                    "THANHTIENINTERNET\n" +
                    "FROM FEETHUHO FH JOIN HOKHAU HK ON FH.IDCANHO = CONVERT(INT,SUBSTRING(HK.DIACHI,1,4))\n" +
                    "\t\tJOIN PHICODINH P ON P.LOAICANHO = FH.IDCANHO\n" +
                    "WHERE MADOTTHU = ? AND HK.MAHOKHAU = ?;";

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, maDotThu);
        stmt.setInt(2, maHoKhau);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new DanhSachThuPhiModel(
                        rs.getString("TENCHUHO"),
                        rs.getInt("GIATIENLOAICANHO"),
                        rs.getInt("FEEQLCHUNGCU"),
                        rs.getInt("TIENXEMAY"),
                        rs.getInt("TIENOTO"),
                        rs.getInt("THANHTIENDIEN"),
                        rs.getInt("TONGSODIEN"),
                        rs.getInt("THANHTIENNUOC"),
                        rs.getInt("TONGSONUOC"),
                        rs.getInt("THANHTIENINTERNET")
                );
            } else {
                return null; // Không có dữ liệu
            }
        }
    }
}

    public void insertDANHSACHTHUPHI(
            int maDotThu, int hoKhau, String chuHo,
            int tienNha, int tienDv,
            int tienXeMay, int tienOto,
            int tienDien, int soDien,
            int tienNuoc, int soNuoc,
            int tienInternet
    ) throws SQLException {
        String query =
                "INSERT INTO DANHSACHTHUPHI(\n" +
                        "MADOTTHU,IDCANHO,CHUHO,\n" +
                        "TIENNHA,TIENDICHVU,\n" +
                        "TIENUNGHO,\n" +
                        "TIENXEMAY,TIENOTO,\n" +
                        "TIENDIEN,SODIEN,\n" +
                        "TIENNUOC,SONUOC,\n" +
                        "TIENINTERNET)\n" +
                        "VALUES(?,?,?,?,?,0,?,?,?,?,?,?,?)";
        try (PreparedStatement st = connection.prepareStatement(query)){
            st.setInt(1,maDotThu);
            st.setInt(2,hoKhau);
            st.setString(3,chuHo);
            st.setInt(4,tienNha);
            st.setInt(5,tienDv);
            st.setInt(6,tienXeMay);
            st.setInt(7,tienOto);
            st.setInt(8,tienDien);
            st.setInt(9,soDien);
            st.setInt(10,tienNuoc);
            st.setInt(11,soNuoc);
            st.setInt(12,tienInternet);
            int s = st.executeUpdate();
            System.out.println("Number of rows inserted: " + s);
        }catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback nếu có lỗi xảy ra
                } catch (SQLException rollbackEx) {
                    System.out.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            System.out.println("Error: " + e.getMessage());
        }
    }
    public ResultSet getChuHo(int maHoKhau){
        String query = "SELECT TENCHUHO FROM HOKHAU K \n" +
                "WHERE K.MAHOKHAU = "+maHoKhau;
        return executeQuery(query);
    }
    public void themDanhSachThuPhi(int maHoKhau, int maKhoanThu, int trangThai, int id) {
        String query = "EXEC INSERT_DONGGOP " + maHoKhau + ", " + maKhoanThu + ", " + trangThai +", "+id;
        executeUpdate(query);
    }

    public void updateFeeCoDinh(int maDotThu,int idCanHo, int tienungho,int tienNha, int tienDichVu, int tienxemay, int tienoto){
        String query = "EXEC UpdateFeesCODINH "+maDotThu+","+idCanHo+","+tienungho+" , "+tienNha+", "+tienDichVu+", "+tienxemay+" ,"+tienoto;
        executeUpdate(query);
    }
    public void setDanhSachFeeThu(int maDotThu,int idCanHo, int tienungho,int tienNha, int tienDichVu, int tienxemay, int tienoto){
        String query = "INSERT INTO DANHSACHTHUPHI(MADOTTHU,IDCANHO,TIENUNGHO,TIENNHA,TIENDICHVU,TIENXEMAY,TIENOTO)\n" +
                "VALUES ("+maDotThu+","+idCanHo+","+tienungho+","+tienNha+","+tienDichVu+","+tienxemay+","+tienoto+")";
        executeUpdate(query);
    }
    public void updateFeeDienNuoc(int maDothu, int idCanHo, int tiendien, int sodien, int tiennuoc, int sonuoc, int tieninternet){
        String query = "EXEC UPDATE_DIENNUOC "+maDothu+","+idCanHo+","+tiendien+","+sodien+","+tiennuoc+","+sonuoc+","+tieninternet;
        executeUpdate(query);
    }
    public void updateUngHoFee(int makhoanthu, int maHoKhau, int tienUngHo){
        String query = "" +
                "EXEC UPDATE_UNGHO "+makhoanthu+" , "+maHoKhau+" , "+ tienUngHo;
        executeUpdate(query);
    }
    public ResultSet getDanhSachKhoanThu() {
        String query = "SELECT * FROM LOAIPHI";
        return executeQuery(query);
    }
    public ResultSet getDanhSachKhoanThuDot() {
        String query = "SELECT * FROM DOTTHUPHI";
        return executeQuery(query);
    }

    public ResultSet getKhoanThuPhi(int maKhoanThu) {
        String query = "SELECT * FROM LOAIPHI\n" +
                "WHERE ID LIKE " + maKhoanThu  ;
        return executeQuery(query);
    }
    public int getIdKhoanThu(int makhoanthu, String tenKhoanThu, int batBuoc, long SotienCanDong, LocalDate ngay, String Mota) {
        int id = -1;
        String query =
                "SELECT LOAIPHI.ID " +
                        "FROM LOAIPHI " +
                        "WHERE MAKHOANTHU = " + makhoanthu + " AND " +
                        "TEN = N'" + tenKhoanThu + "' AND " +
                        "BATBUOC = " + batBuoc + " AND " +
                        "SOTIENTRENMOTNGUOI = " + SotienCanDong + " AND " +
                        "NGAYTAO = '" + ngay.toString() + "' AND " +
                        "MOTA = N'" + Mota + "';";

        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                id = resultSet.getInt(1);
                System.out.println("ID KhoanThu: "+id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public ResultSet getKhoanThuDot(int maKhoanThuDot){
        String query = "SELECT * FROM DOTTHUPHI\n" +
                "WHERE MADOTTHU LIKE "+maKhoanThuDot;
        return executeQuery(query);
    }
    public ResultSet danhSachKhoanThu_timKiem(String condition) {
        String query = "SELECT * FROM LOAIPHI\n" +
                "WHERE MAKHOANTHU LIKE '%" + condition + "%' OR TEN LIKE N'%" + condition + "%' OR ID LIKE '%"+condition+"%'";
        return executeQuery(query);
    }
    public ResultSet danhSachKhoanThu_timKiemDot(String condition) {
        String query = "SELECT * FROM DOTTHUPHI\n" +
                "WHERE MADOTTHU LIKE '%" + condition + "%' OR TEN LIKE N'%" + condition + "%' OR NGAYTAO LIKE '%"+condition+"%'";
        return executeQuery(query);
    }

    public int getSoLuongHoDaDongPhi(int id) {

        int res = 0;

        String query = "SELECT COUNT(MAHOKHAU) FROM DONGGOP\n" +
                "WHERE ID LIKE '" + id + "' AND TRANGTHAI = 1";
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                res = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public int getSoLuongHoChuaDongPhi(int id) {

        int res = 0;

        String query = "SELECT COUNT(MAHOKHAU) FROM DONGGOP\n" +
                "WHERE ID LIKE " + id + " AND TRANGTHAI = 0";
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                res = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
    public int getSoLuongHoChuaDongPhiDot(int id) {
        int res = 0;

        String query = "SELECT COUNT(IDCANHO) FROM DANHSACHTHUPHI\n" +
                "WHERE MADOTTHU LIKE '" + id + "' AND TRANGTHAI = 0";
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                res = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public int getSoLuongHoDongPhi(int id) {

        int res = 0;

        String query = "SELECT COUNT(MAHOKHAU) FROM DONGGOP\n" +
                "WHERE ID = " + id;
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                res = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public int getTongSoLuongHoDongPhiDot(int id) {

        int res = 0;

        String query = "SELECT COUNT(IDCANHO) FROM DANHSACHTHUPHI\n" +
                "WHERE MADOTTHU = " + id;
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                res = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
    public int getSoLuongHoDongPhiDot(int id) {

        int res = 0;

        String query = "SELECT COUNT(IDCANHO) FROM DANHSACHTHUPHI\n" +
                "WHERE MADOTTHU = " + id +"AND TRANGTHAI =1";
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                res = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public void deleteKhoanThuPhi(int maKhoanThu) {
        String query1 = "DELETE DONGGOP\n" +
                "WHERE MAKHOANTHU LIKE '" + maKhoanThu + "'";
        String query2 = "DELETE LOAIPHI\n" +
                "WHERE MAKHOANTHU LIKE '" + maKhoanThu + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteDotThuPhi(int maDotThu) {
        String query1 = "DELETE FROM DONGGOP WHERE MAKHOANTHU = ?";
        String query2 = "DELETE FROM LOAIPHI WHERE MAKHOANTHU = ?";
        String query3 = "DELETE FROM DANHSACHTHUPHI WHERE MADOTTHU = ?";

        try (PreparedStatement stmt1 = connection.prepareStatement(query1);
             PreparedStatement stmt2 = connection.prepareStatement(query2);
             PreparedStatement stmt3 = connection.prepareStatement(query3)) {

            // Set the parameters for each query
            stmt1.setInt(1, maDotThu);
            stmt2.setInt(1, maDotThu);
            stmt3.setInt(1, maDotThu);

            // Execute the queries
            stmt1.executeUpdate();
            stmt2.executeUpdate();
            stmt3.executeUpdate();
        } catch (SQLException e) {
            // Log detailed error message
            System.err.println("Error while deleting DotThuPhi with maDotThu = " + maDotThu);
            e.printStackTrace();
            throw new RuntimeException("Failed to delete DotThuPhi", e);
        }
    }

    public ResultSet getTongSoTienDaThuPhi(){
        ResultSet resultSet = null;
        Statement statement;
        String query = "SELECT SUM(SOTIENDADONG) FROM DONGGOP WHERE TRANGTHAI = 1";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getDanhSachChuaDongPhi(int maKhoanThu) {
        String query = "select MAHOKHAU, TENCHUHO, DIACHI, SOTHANHVIEN, SOTIENCANDONG\n" +
                "from DONGGOP\n" +
                "WHERE ID = " + maKhoanThu + " AND TRANGTHAI = 0";
        return executeQuery(query);
    }
    public ResultSet getDanhSachChuaDongPhiDot( int madothu) {
        String query = "SELECT IDCANHO, CHUHO, HK.DIACHI, (TIENNHA+TIENDICHVU+TIENXEMAY+TIENOTO+TIENDIEN+TIENNUOC+TIENINTERNET) AS [SOTIENCANDONG], MADOTTHU\n" +
                "FROM DANHSACHTHUPHI DS JOIN HOKHAU HK ON DS.IDCANHO = HK.MAHOKHAU\n" +
                "WHERE  MADOTTHU = "+madothu +"  AND TRANGTHAI = 0";
        return executeQuery(query);
    }

    public ResultSet danhSachChuaDongPhi_timKiem(int ID, String q) {
        String query = "select MAHOKHAU, TENCHUHO, DIACHI, SOTHANHVIEN, SOTIENDADONG\n" +
                "from DONGGOP\n" +
                "WHERE ID =" + ID + "AND TRANGTHAI = 0\n" +
                "AND (TENCHUHO LIKE N'%" + q + "%' OR DIACHI LIKE N'%" + q + "%' OR MAHOKHAU LIKE '%"+q+"%')";
        return executeQuery(query);
    }

    public void updateNopPhi(int maHoKhau, int maKhoanThu, String soTien) {
        String query = "UPDATE DONGGOP\n" +
                "SET TRANGTHAI = 1, NGAYDONG = GETDATE(), SOTIENDADONG = " + soTien + "\n" +
                "WHERE MAHOKHAU = " + maHoKhau + " AND MAKHOANTHU = " + maKhoanThu;
        executeUpdate(query);
    }
    public void updateNopPhiUNGHO(int maHoKhau, int maKhoanThu, String soTien,int id) {
        String query = "UPDATE DONGGOP\n" +
                "SET TRANGTHAI = 1, NGAYDONG = GETDATE(), SOTIENDADONG = " + soTien + "\n" +
                "WHERE MAHOKHAU = " + maHoKhau + " AND MAKHOANTHU = " + maKhoanThu +"AND ID ="+id;
        executeUpdate(query);
    }
    public void updateNopPhiTong(int maDotthu, int idHokhau,int tongSoTien){
        String query = "UPDATE DANHSACHTHUPHI \n"+
                "SET TRANGTHAI = 1, NGAYDONG = GETDATE(), SOTIENDADONG = "+ tongSoTien +"\n"+
                "WHERE MADOTTHU = "+maDotthu+" AND IDCANHO = "+idHokhau;
        executeUpdate(query);
    }
    public String getNgayNopPhi(int maHoKhau, int maKhoanThu,int id) {
        String query = "SELECT NGAYDONG FROM DONGGOP\n" +
                "WHERE MAHOKHAU = " + maHoKhau + " AND MAKHOANTHU = " + maKhoanThu +"AND ID = "+id;
        String ngayNopPhi = "";
        ResultSet resultSet = executeQuery(query);
        try {
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                ngayNopPhi = resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ngayNopPhi;
    }

    public ResultSet danhSachDaDongPhi_timKiem(String q, int ID) {
        String query = "select MAHOKHAU, TENCHUHO, DIACHI, SOTHANHVIEN, SOTIENDADONG\n" +
                "from DONGGOP\n" +
                "WHERE ID =" + ID + "AND TRANGTHAI = 1\n" +
                "AND (TENCHUHO LIKE N'%" + q + "%' OR DIACHI LIKE N'%" + q + "%' OR MAHOKHAU LIKE '%"+q+"%')";
        return executeQuery(query);
    }
    public ResultSet danhSachDaDongPhi_timKiemDot(String q, int maDotThu) {
        String query = "select DANHSACHTHUPHI.IDCANHO, DANHSACHTHUPHI.CHUHO, K.DIACHI,  DANHSACHTHUPHI.SOTIENDADONG, DANHSACHTHUPHI.NGAYDONG\n" +
                "from DANHSACHTHUPHI join HOKHAU K ON DANHSACHTHUPHI.IDCANHO = K.MAHOKHAU\n" +
                "WHERE MADOTTHU =" + maDotThu + "AND TRANGTHAI = 1\n" +
                "AND (CHUHO LIKE N'%" + q + "%' OR IDCANHO LIKE '%" + q + "%' OR NGAYDONG LIKE N'%"+q+"%')";
        return executeQuery(query);
    }
    public ResultSet danhSachChuaDongPhi_timKiemDot(String q, int maDotThu) {
        String query = "select DANHSACHTHUPHI.IDCANHO, DANHSACHTHUPHI.CHUHO, K.DIACHI,  DANHSACHTHUPHI.SOTIENDADONG, DANHSACHTHUPHI.NGAYDONG\n" +
                "from DANHSACHTHUPHI join HOKHAU K ON DANHSACHTHUPHI.IDCANHO = K.MAHOKHAU\n" +
                "WHERE MADOTTHU =" + maDotThu + "AND TRANGTHAI = 0\n" +
                "AND (CHUHO LIKE N'%" + q + "%' OR IDCANHO LIKE '%" + q + "%' OR NGAYDONG LIKE N'%"+q+"%')";
        return executeQuery(query);
    }

    public ResultSet getDanhSachDaDongPhi(int maKhoanThu, int id) {
        String query = "Select MAHOKHAU, TENCHUHO, DIACHI, SOTHANHVIEN, SOTIENDADONG\n" +
                "from DONGGOP\n" +
                "WHERE MAKHOANTHU = " + maKhoanThu + " AND TRANGTHAI = 1 AND ID = "+id;
        return executeQuery(query);
    }
    public ResultSet getDanhSachDaDongPhiDot(int maDotThu) {
        String query = "Select MADOTTHU, IDCANHO,CHUHO ,DIACHI, SOTIENDADONG, NGAYDONG\n" +
                "from DANHSACHTHUPHI ds JOIN HOKHAU HK ON HK.MAHOKHAU = ds.IDCANHO \n" +
                "WHERE MADOTTHU = " + maDotThu + " AND TRANGTHAI = 1";
        return executeQuery(query);
    }
    public ResultSet getDSNguoiChet() {
        String query = "select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU\n" +
                "from NHANKHAU INNER JOIN KHAITU ON NHANKHAU.MANHANKHAU = KHAITU.MANHANKHAUNGUOICHET";

        return executeQuery(query);
    }

    public ResultSet deadNhanKhau_timkiem(String condition) {
        String query = "select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU\n" +
                "from NHANKHAU INNER JOIN KHAITU ON NHANKHAU.MANHANKHAU = KHAITU.MANHANKHAUNGUOICHET\n" +
                "WHERE MANHANKHAU LIKE '%" + condition + "%' OR SOCANCUOC LIKE '%" + condition + "%' OR HOTEN LIKE N'%" + condition + "%'";

        return executeQuery(query);
    }
    public ResultSet getThongTinKhaiTu(String maNhanKhauNguoiChet) {
        String query = "SELECT KT.MAGIAYKHAITU, NK1.MANHANKHAU, NK1.HOTEN, NK2.MANHANKHAU, NK2.HOTEN, NK2.SOCANCUOC, NK2.NGAYSINH, NK2.GIOITINH, NK2.DANTOC, NK2.QUOCTICH,\n" +
                "\tNK2.NGUYENQUAN, NK2.NOITHUONGTRU, KT.NGAYKHAI, KT.NGAYCHET, KT.LYDOCHET\n" +
                "FROM KHAITU KT INNER JOIN NHANKHAU NK1 ON KT.MANHANKHAUNGUOIKHAI = NK1.MANHANKHAU\n" +
                "\tINNER JOIN NHANKHAU NK2 ON KT.MANHANKHAUNGUOICHET = NK2.MANHANKHAU\n" +
                "WHERE KT.MANHANKHAUNGUOICHET = " + maNhanKhauNguoiChet;
        return executeQuery(query);
    }

    public void updateThongTinKhaiTu(String maGiayKhaiTu, String ngayKhai, String ngayChet, String lyDo) {
        String query = "UPDATE KHAITU\n" +
                "SET NGAYKHAI = '" + ngayKhai + "', NGAYCHET = '" + ngayChet + "', LYDOCHET = N'" + lyDo + "'\n" +
                "WHERE MAGIAYKHAITU = " + maGiayKhaiTu;
        executeUpdate(query);
    }
    /***************************************************************************/

    public ResultSet getNumberOfCacLoaiPhi(){
        ResultSet resultSet = null;
        Statement statement;
        String query = "SELECT COUNT(MAKHOANTHU) FROM LOAIPHI";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    public  void xoaTamVang(int magiaytamvang){
        String query = "DELETE TAMVANG  WHERE MAGIAYTAMVANG = " + magiaytamvang;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("deo xoa duoc");
        }
    }


    public int xoaNhanKhau(String soNhanKhau) {
        String query = "DECLARE @OUTPUT INT\n" +
                "EXEC DELETE_NHANKHAU " + soNhanKhau + ", @OUTPUT OUTPUT\n" +
                "SELECT @OUTPUT";
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int getTongUngHoById(int maKhoanThu, int id) throws SQLException {
        String query =
                "SELECT SUM(SOTIENDADONG) AS [TONGTIEN] FROM DONGGOP\n" +
                        "WHERE MAKHOANTHU = "+ maKhoanThu + " AND ID = "+id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getInt(1);
    }
    public int getTongThuPhiByDot(int maDot) throws SQLException {
        String query =
                "SELECT SUM(SOTIENDADONG) AS [TONGTIEN] FROM DANHSACHTHUPHI\n" +
                        "WHERE MADOTTHU = "+ maDot;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getInt(1);
    }
    public FeeHoaDon getFeeHoaDon(int maDotThu, int idCanHo) throws SQLException {
        String query =
                "SELECT CHUHO, SUBSTRING(HOKHAU.DIACHI,1,4) AS[IDCANHO], danhsachthuphi.MADOTTHU, " +
                        "TEN, TIENDICHVU, TIENNHA, " +
                        "TIENDIEN, TIENNUOC, TIENINTERNET, " +
                        "TIENXEMAY, TIENOTO, " +
                        "CASE " +
                        "    WHEN DIACHI LIKE '%01%' OR DIACHI LIKE '%02%' OR DIACHI LIKE '%03%' THEN 60 " +
                        "    WHEN DIACHI LIKE '%04%' OR DIACHI LIKE '%05%' THEN 90 " +
                        "    ELSE 360 " +
                        "END AS DIENTICH, " +
                        "XEMAY, OTO, " +
                        "SONUOC, SODIEN, NGAYDONG, " +
                        "CAST(TIENDICHVU + TIENNHA + TIENDIEN + TIENNUOC + TIENINTERNET + TIENXEMAY + TIENOTO AS VARCHAR) AS TONGSOTIEN " +
                        "FROM DANHSACHTHUPHI " +
                        "JOIN DOTTHUPHI ON DANHSACHTHUPHI.MADOTTHU = DOTTHUPHI.MADOTTHU " +
                        "JOIN HOKHAU ON HOKHAU.MAHOKHAU = DANHSACHTHUPHI.IDCANHO " +
                        "WHERE DANHSACHTHUPHI.MADOTTHU = ? AND IDCANHO = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, maDotThu);
            stmt.setInt(2, idCanHo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new FeeHoaDon(
                            rs.getString("CHUHO"),
                            rs.getString("IDCANHO"),
                            rs.getInt("MADOTTHU"),
                            rs.getString("TEN"),
                            rs.getInt("TIENNHA"),
                            rs.getInt("TIENDICHVU"),
                            rs.getInt("TIENDIEN"),
                            rs.getInt("TIENNUOC"),
                            rs.getInt("TIENINTERNET"),
                            rs.getInt("TIENXEMAY"),
                            rs.getInt("TIENOTO"),
                            rs.getInt("DIENTICH"),
                            rs.getInt("XEMAY"),
                            rs.getInt("OTO"),
                            rs.getInt("SODIEN"),
                            rs.getInt("SONUOC"),
                            rs.getString("NGAYDONG"),
                            rs.getString("TONGSOTIEN")
                    );
                } else {
                    return null; // No data found
                }
            }
        }
    }
    public boolean checkKhaiTu(String maNhanKhau) {
        String query = "SELECT COUNT(MAGIAYKHAITU) FROM KHAITU WHERE MANHANKHAUNGUOICHET = " + maNhanKhau;
        ResultSet resultSet = executeQuery(query);
        try {
            resultSet.next();
            if (resultSet.getInt(1) == 0) return true; // chua chet
            else return false; // da chet
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkTamVang(String maNhanKhau) {
        String query = "SELECT COUNT(MAGIAYTAMVANG) FROM TAMVANG WHERE MANHANKHAU = " + maNhanKhau;
        ResultSet resultSet = executeQuery(query);
        try {
            resultSet.next();
            if (resultSet.getInt(1) == 0) return true; // chua di tam vang
            else return false;// da da di tam vang
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}


