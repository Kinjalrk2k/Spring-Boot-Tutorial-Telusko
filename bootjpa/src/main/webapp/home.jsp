<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
  </head>
  <body>
    <h1>Add Alien</h1>
    <form action="addAlien">
      <label for="aid">Alien ID</label>
      <input type="text" name="aid" /><br />

      <label for="aname">Alien Name</label>
      <input type="text" name="aname" /><br />

      <input type="submit" /><br />
    </form>

    <hr />

    <h1>Find Alien</h1>
    <form action="getAlien">
      <label for="aid">Alien ID</label>
      <input type="text" name="aid" /><br />

      <input type="submit" /><br />
    </form>
  </body>
</html>
