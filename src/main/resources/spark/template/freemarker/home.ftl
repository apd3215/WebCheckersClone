<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

    <#if signed ??>
        <h3> Available Players:</h3>
      <#list signed as person>
        <#if person != currentUser.name>
            <form method="post" action="/game">
            <!-- <a id = "otherPlayer" href = "/game"> </a> --->
            <input name="otherPlayer" type="submit" value=${person} >
            <br> <br>
          </form>
        </#if>
      </#list>
    <#else>
      <p>
        <#if num == 1>
          ${num} Player signed in.
        <#else>
          ${num} Players signed in.
        </#if>
      </p>
    </#if>

    <#if playing ??>
        <h3> Players inGame:</h3>
      <#list playing as person>
          <#if person != currentUser.name>
              <form method="get" action="/spectator/game">  <!--- change path to /spectate after UI components --->
                  <!-- <a id = "otherPlayer" href = "/game"> </a> --->
                  <input name="otherPlayer" type="submit" value=${person} >
                  <br> <br>
              </form>
          </#if>
      </#list>
    </#if>

    <#if spectating ??>
      <h3> Players Spectating:</h3>
      <#list spectating as person>
          <#if person != currentUser.name>
              <p> person </p>
          </#if>
      </#list>
    </#if>




    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->

  </div>

</div>
</body>

</html>
