<%-- 
    Document   : Spectacles
    Created on : 14 févr. 2017, 16:04:46
    Author     : yassinea
--%>
<%@page import="Controleurs.ClasseHelp"%>
<%@page import="DAO.DateFinaleDAO"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="Modeles.SpectacleDatePhoto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String Min = (String) request.getSession().getAttribute("dateMin");
    String Max = (String) request.getSession().getAttribute("dateMax");
%>
<% String MinAff = (String) request.getSession().getAttribute("dateMinFiltrage");
    String MaxAff = (String) request.getSession().getAttribute("dateMaxFiltrage");
%>

<% List<SpectacleDatePhoto> listeSpectacleDatePhoto = (List<SpectacleDatePhoto>) request.getSession().getAttribute("listeSpectacles");
    Integer nombreSpe = listeSpectacleDatePhoto.size();%>
<%  String tousLesSpectacles = (String) request.getAttribute("tousLesSpectacles");
    boolean tousLesSpectaclesBool = false;
    if (tousLesSpectacles != null) {
        tousLesSpectaclesBool = tousLesSpectacles.equals("tousLesSpectacles");
    }
    if (tousLesSpectaclesBool) {%>
<h2> L'ensemble des spectacles proposés : </h2> <%}%>
<% if (nombreSpe == 0 && !tousLesSpectaclesBool) {%>
<h2> Désolé! Aucun spectacle n'a été trouvé pour les dates et le type sélectionnés.</h2><br/>
<% }%>
<%if (nombreSpe == 1 && !tousLesSpectaclesBool) {%>
<h2> <%=nombreSpe%> spectacle a été trouvé pour les dates et le type sélectionnés.</h2><br/>
<% }%>

<%if (nombreSpe > 1 && !tousLesSpectaclesBool) {%>
<h2> <%=nombreSpe%> spectacles ont été trouvés pour les dates et le type sélectionnés.</h2><br/>
<% }%>
<%for (SpectacleDatePhoto spect : listeSpectacleDatePhoto) {
        String str = ClasseHelp.ListeDatesToString(spect.getListeDate());
%>
<div class="spectacleMiniFiche">
    <a href="/Mytheatre/Detail?nospectacle=<%=spect.getNumeroSpectacle()%>"><img class="photoSpectacle" src="<%String url = "Images/spectacle0.jpg";
        if (spect.getPhotos().size() > 0) {
            url = spect.getPhotos().get(0).toString();
        };%><%=url%>" height="200" width="200" title="<%=str%>" onerror="imgError(this)"/></a><br>
    <a class="titrePhoto" href="/Mytheatre/Detail?nospectacle=<%=spect.getNumeroSpectacle()%>"><%=spect.getNomSpectacle()%> </a>

</div>
<% }%>
<div class="clearMiniFiche" ></div>
