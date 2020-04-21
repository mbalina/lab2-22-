let N_SIZE = 8,
  playerName = "",
  base_url = "http://127.0.0.1:8080/game"
  EMPTY = '&nbsp;',
  boxes = [],
  turn = 'X',
  checkGame = setInterval(updateGame, 1000),
  game = {
    "gameId": null,
    "playerId1": "",
    "playerId2": "",
    "winner" : null,
    "turn" : null,
    "field" : [
        [0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0]
    ]
  };

/**
 * Initializes the Tic Tac Toe board and starts the game.
 */
function init() {
  let board = document.createElement('table');
  board.setAttribute('border', 1);
  board.setAttribute('cellspacing', 0);
  board.classList.add('field')

  let identifier = 1;
  for (let i = 0; i < N_SIZE; i++) {
    let row = document.createElement('tr');
    board.appendChild(row);
    for (let j = 0; j < N_SIZE; j++) {
      let cell = document.createElement('td');
      cell.setAttribute('height', 50);
      cell.setAttribute('width', 50);
      cell.setAttribute('align', 'center');
      cell.setAttribute('valign', 'center');
      cell.classList.add('col' + j, 'row' + i);
      if (i == j) {
        cell.classList.add('diagonal0');
      }
      if (j == N_SIZE - i - 1) {
        cell.classList.add('diagonal1');
      }
      cell.identifier = identifier;
      cell.addEventListener('click', set);
      row.appendChild(cell);
      boxes.push(cell);
      identifier += identifier;
    }
  }

  document.getElementById('tictactoe').appendChild(board);
  startNewGame();
}

/**
 * New game
 */
function startNewGame() {
    // send request to new Game
    if (playerName === "") {
      playerName = prompt("Input player name:", "")
      startNewGame()
      return
    } else {
      document.getElementById('playerName').textContent = 'Player name: ' + playerName
    }
    let request = new XMLHttpRequest();
    request.open('POST', base_url + '/{playerId}?playerId='+playerName);
    request.setRequestHeader('Content-Type', 'application/x-www-form-url');
    request.addEventListener("readystatechange", function(request) {
      request = request.srcElement
      if (request.readyState === 4 && request.status === 200) {
        console.log(request.responseText)
        game = JSON.parse(request.responseText);
        document.getElementById('symbol').textContent = 'Your symbol: ' + (game.playerId1 == playerName ? 'X' : 'O') 
      }
    });
    request.send(); 
    turn = 'X';
    boxes.forEach(function (square) {
        square.innerHTML = EMPTY;
    });
}

function updateGame() {
  if (game.gameId == null) return
  let request = new XMLHttpRequest();
  request.open('GET', base_url + '/{gameId}?gameId='+game.gameId);
  request.setRequestHeader('Content-Type', 'application/x-www-form-url');
  request.addEventListener("readystatechange", function(request) {
    request = request.srcElement
    if (request.readyState === 4 && request.status === 200) {
      console.log("fun")
      console.log(request.responseText)
      game = JSON.parse(request.responseText);
      document.getElementById('oponnent').textContent = 'Oponnent name: ' + (game.playerId1 == playerName? game.playerId2 : game.playerId1) 
      if (game.turn != "null")
        document.getElementById("turn").textContent = "Turn: " + game.turn
      if (game.winner != null) {
        alert("Winner: " + game.winner)
        clearInterval(checkGame)
        restartGame()
      }
      if (game.field != null) {
        setField()
      }
    }
  });
  request.send();
}

/**
 * Sets clicked square and also updates the turn.
 */
function set() {
  if (game.playerId1 == "" || game.playerId2 == "") {
    alert("Wait player2...")
  }
  let col = parseInt(this.className.split()[0][3])
  let row = parseInt(this.className.split()[0][8])

  let request = new XMLHttpRequest();
  request.open('POST', base_url + '/cell/{gameId}&{playerId}&{row}&{col}?gameId='+game.gameId +
  '&playerId=' + playerName + '&row=' + row + '&col=' + col);
  request.setRequestHeader('Content-Type', 'application/x-www-form-url');
  request.addEventListener("readystatechange", function(request) {
    if (request.readyState === 4 && request.status === 200) {

    }
  });
  request.send();
  // sendReq col row playerId and setField()
  // if user won then create alert and send request to restartGame
}

function restartGame() {
  let request = new XMLHttpRequest();
  request.open('POST', base_url + '/restart/{gameId}?gameId='+game.gameId);
  request.setRequestHeader('Content-Type', 'application/x-www-form-url');
  request.addEventListener("readystatechange", function(request) {
    request = request.srcElement
    if (request.readyState === 4 && request.status === 200) {
      checkGame = setInterval(updateGame, 1000)
      // game restarted
    }
  });
  request.send();
}

function setField() {
    for (let i = 0; i < N_SIZE; i++) {
        for (let j = 0; j < N_SIZE; j++) {
            let cell = document.getElementsByClassName('row'+i + ' col'+j)[0]
            if (game.field[i][j] == 1)
                cell.textContent = 'X'
            if (game.field[i][j] == 2)
                cell.textContent = 'O'
            if (game.field[i][j] == 0)
                cell.textContent = ''
        }
    }
}

init();