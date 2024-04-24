<%@page import="proto.TableColumn"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="proto.TableData"%>
<%@page import="proto.UserTableData"%>
<%@page import="proto.User"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>User HomePage</title>
<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<style>
@import
	url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&display=swap')
	;

*, *:after, *:before {
	box-sizing: border-box;
}

body {
	font-family: "Inter", sans-serif;
	line-height: 1.5;
	min-height: 75vh;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	padding-top: 5vh;
	padding-bottom: 5vh;
	background-color: white;
	margin: 0;
}

ul {
	list-style: none;
	margin: 0;
	padding: 0;
	margin-right: auto;
	background-color: black;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	padding: .75rem;
	margin-bottom: 109%;
	border-radius: 10px;
	box-shadow: 0 10px 50px 0 rgba(5, 4, 62, .25);
}

li:nth-child(6) {
	margin-top: 5rem;
	padding-top: 1.25rem;
	border-top: 1px solid #363664;
}

li+li {
	margin-top: .75rem;
}

a {
	color: #FFF;
	text-decoration: none;
	display: flex;
	align-items: center;
	justify-content: center;
	width: 3rem;
	height: 3rem;
	border-radius: 8px;
	position: relative;
}

a:hover, a:focus, a.active {
	background-color: #30305a;
	outline: 0;
}

a:hover span, a:focus span, a.active span {
	transform: scale(1);
	opacity: 1;
}

i {
	font-size: 1.375rem;
}

span {
	position: absolute;
	background-color: #30305a;
	white-space: nowrap;
	padding: .5rem 1rem;
	border-radius: 6px;
	left: calc(100% + 1.5rem);
	transform-origin: center left;
	transform: scale(0);
	opacity: 0;
	transition: .15s ease;
}

span:before {
	content: "";
	display: block;
	width: 12px;
	height: 12px;
	position: absolute;
	background-color: #30305a;
	left: -5px;
	top: 50%;
	transform: translateY(-50%) rotate(45deg);
	border-radius: 3px;
}

.profile-section {
	align-items: center;
	justify-content: center;
	background-color: black;
	position: relative;
	margin-left: auto;
}

.user-img {
	border-radius: 50%;
	width: 40px;
	height: 40px;
	cursor: pointer;
	margin-right: 5px;
}

.user-details {
	position: fixed;
	top: 0;
	right: 0;
	padding: 20px;
	background-color: #fff;
	border-left: 1px solid #ccc;
	box-shadow: -5px 0 15px rgba(0, 0, 0, 0.1);
	display: none;
	transition: display 0.5s ease;
}

.user-details.active {
	display: block;
}

.user-details img {
	border-radius: 50%;
	width: 80px;
	height: 80px;
}

.settings-btn {
	cursor: pointer;
	margin-left: 10px; /* Adjusted margin */
	padding: 10px;
	display: flex;
	align-items: center;
}

.user-details {
	position: fixed;
	top: 0;
	right: 0;
	padding: 20px;
	background-color: #fff;
	border-left: 1px solid #ccc;
	box-shadow: -5px 0 15px rgba(0, 0, 0, 0.1);
	display: none;
	transition: display 0.5s ease;
}

.user-details.active {
	display: block;
}

.settings-section {
	display: none;
	position: fixed;
	left: 20px;
	bottom: 20px;
	background-color: #fff;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	padding: 10px;
	border-radius: 5px;
	z-index: 1;
}

.settings-section.active {
	display: block;
}

summary::-webkit-details-marker {
	display: none;
}

summary:hover, summary:focus {
	background-color: mix(#000, #185adb, 20%);
}

i {
	font-size: 1.375em;
	margin-left: .25em;
}

a img {
	width: 24px;
	height: 24px;
	background: white;
}

@
keyframes scale { 0% {
	transform: scale(0);
}

100






%
{
transform






:






scale




(






1






)




;
}
}
@media only screen and (max-width: 600px) {
	.profile-section {
		flex-direction: column-reverse;
	}
	.settings-section {
		left: 50%;
		bottom: 0;
		transform: translateX(-50%);
	}
}

@media only screen and (max-width: 400px) {
	body {
		padding: 2vh;
	}
	ul {
		margin-bottom: 50%;
	}
}
</style>
</head>
<body>
	<div class="profile-section">
		<a href="javascript:void(0);" onclick="toggleUserDetails()"> <i
			class="fa fa-user"></i> <span>Profile</span>
		</a> <a href="settings.jsp"><i class="fas fa-cog"></i><span>Settings</span></a>
	</div>

	<div class="user-details" id="userDetails">
	<form action="logout" method="post">
        <button class="logout-btn" type="submit">Logout</button>
    </form>
	</div>
	<ul>
		<li><a href="#"> <i class="fas fa-home"></i> <span>DashBoard</span></a></li>
		<li><a href="#"> <i class="fa fa-comment"></i><span>CHATS</span></a></li>
		<li><a href="#"> <i class="fa fa-hashtag"></i><span>CHANNELS</span></a></li>
		<li><a href="#"> <i class="fa fa-clipboard"></i><span>NOTE-</span></a></li>
		<li><a href="#"><i class="fa fa-file"></i><span>RESOURCES</span></a></li>
		<li><a href="#"> <i class="fa fa-bell"></i><span>NOTIFICATIONS</span></a></li>
	</ul>
	
	

	<script>
		function toggleUserDetails() {
			var userDetails = document.getElementById("userDetails");
			userDetails.classList.toggle("active");
		}
	</script>
</body>
</html>
