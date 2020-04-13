<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--
Design by TEMPLATED
http://templated.co
Released for free under the Creative Commons Attribution License

Name       : Coefficient
Description: A two-column, fixed-width design with dark color scheme.
Version    : 1.0
Released   : 20131117

-->

<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
<link href="css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/bootstrap.main.css" rel="stylesheet" type="text/css" media="all" />
<!--	<link rel="stylesheet" th:href="@{/css/fonts.css}">-->
<!--	<link rel="stylesheet" th:href="@{/css/default.css}">-->
<!--[if IE 6]>
<link href="/css/default_ie6.css" rel="stylesheet" type="text/css" />
<![endif]-->
</head>
<body>
<header th:insert="blocks/head::header"></header>

		<div>

       	  <c:if test="${namePage=='index'}">
						<div class="index">

						</div>
         </c:if>




		<c:if test="${namePage=='viewdate'}">
			<div class="viewdate">
		<br/>
		<div>
			<h2> Данные о сотрудниках страхователей после обработки</h2>
		</div>

						 <form action="#" th:action="@{/greeting}" th:object="${greeting}" method="post">
						 <table border = 1 >

										 <tr>
											<th>
													UUID Пачки
												</th>
												<th>
													UUID Записи
												</th>
											 <th>
													 СНИЛС
											 </th>
											 <th>
													 Фамилия
												</th>
												<th>
												 Имя
												 </th>
												 <th>
												 Отчество
												 </th>
												 <th>
															Дата рождения
												 </th>


												 <th>
															Страна
												 </th>
												 <th>
															область
												 </th>
												 <th>
															Регион
												 </th>
												 <th>
															Город
												 </th>

										 </tr>
										 <c:forEach items="${employees}" var="employeeinfo">
										 <tr>
													<c:forEach var="count" begin="0" end="10">
															<td>
																	 <c:choose>
																			<c:when test="${(count==7)||(count==8)||(count==9)||(count==10)}">
																					<c:choose>
																						<c:when test="${fn:split(employeeinfo,',')[count]=='-'}">
																													<!-- <p><input type="text" value="${fn:split(employeeinfo,',')[count]}" /></p> -->
																						</c:when>
																						<c:otherwise>
																							<c:out value="${fn:split(employeeinfo,',')[count]}"/>
																						</c:otherwise>
																					</c:choose>
																			</c:when>
																			<c:otherwise>
																					<c:out value="${fn:split(employeeinfo,',')[count]}"/>
																			</c:otherwise>
																	 </c:choose>
														</td>
													</c:forEach>
										 </tr>
										 </c:forEach>

						 </table>
							<p><input type="submit" value="Сохранить" /> </p>

							</form>
							</div>
					</c:if>
					 <c:if test="${namePage=='getdate'}">
              <div class="getdate">
							    <h2>${massege} </h2>
	            </div>
					 </c:if>



				<c:if test="${namePage=='upload'}">
          <div class="uploadFile">
					 <form:form method="post" enctype="multipart/form-data" modelAttribute="uploadedFile" action="uploadFile">
							<table>
								<tr>
									<td>Укажите путь к файлу для загрузки</td>
									<td><input type="file" name="file" /></td>
									<td style="color: red; font-style: italic;">
									<form:errors path="file" />
									</td>
								</tr>
								<tr>
							<td></td>
							<td><input type="submit" value="Загрузить файл СЗВ-К" /></td>
							<td></td>
								</tr>
							</table>
				   </form:form>

           </div>
				</c:if>


		</div>

	</div>
	<br/>


<footer th:insert="blocks/footer::footer"></footer>
</body>
</html>
