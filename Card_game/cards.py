import random

class Dealer:
  def __init__(self):
    self.deck = [
      '♥A', '♥7', '♥8', '♥9', '♥10', '♥J', '♥Q', '♥K',
      '♦A', '♦7', '♦8', '♦9', '♦10', '♦J', '♦Q', '♦K',
      '♣A', '♣7', '♣8', '♣9', '♣10', '♣J', '♣Q', '♣K',
      '♠A', '♠7', '♠8', '♠9', '♠10', '♠J', '♠Q', '♠K'
    ]
    self.players = []

  def shuffle(self):
    length = len(self.deck)
    for i in range (0, length):
        randomNumber = random.randint(0, length - 1)
        self.deck[i], self.deck[randomNumber] = self.deck[randomNumber], self.deck[i]
    print(self.deck)

  def deal(self, n):
    myList = []
    for i in range(0, n):
        if(len(self.deck) == 0):
            return myList
        myList.append(self.deck[0])
        self.deck.pop(0)
    return myList

  def addPlayer(self, player):
    self.players.append(player)

  def removePlayer(self, player):
    self.players.remove(player)

  def startRound(self):
    for i in self.players:
        i.needsCard()
        print(i.acceptCard(self.deal(1)))
    go = True
    while go == True:
        go = False
        print("--- rozdávam ---")
        for i in self.players:
            if(i.needsCard()):
                print(i.acceptCard(self.deal(1)))
                go = True
    self.announceWinner()

  def announceWinner(self):
    print("--- kolo skončilo ---")
    for i in range(1, len(self.players)):
        currentvalue = self.players[i].getHandValue()
        position = i
        while position > 0 and self.players[position - 1] in self.players:
            print(position)
            if(self.players[position - 1].getHandValue() > currentvalue):
                self.players[position]=self.players[position - 1]
            position = position-1
            self.players[position]=currentvalue
    for i in self.players:
        print(i.getHandValue())




class Player:
  def __init__(self, name, strategy):
    self.name = name
    self.strategy = strategy
    self.hand = []

  def getHandValue(self):
    sum = 0
    for i in self.hand:
        if(i[1] == 'A'):
            sum += 11
        elif(i[1] in ['J', 'Q', 'K']):
            sum += 1
        else:
            sum += int(i[1:])
    return sum

  def acceptCard(self, cards):
    self.hand.extend(cards)
    str = self.name 
    str += " má nyní karty: "
    for i in self.hand:
        str += i + " "
    return str

  def needsCard(self):
    if(self.getHandValue() == 0):
        self.check = 0
        return True
    elif(self.strategy == 'Cautions'):
        if(self.getHandValue() <= 10):
            return True
    elif(self.strategy == 'Bold'):
        if(self.getHandValue() <= 15):
            return True
    elif(self.strategy == 'Human'):
        if(self.check == 0):
            if(self.getHandValue() <= 21):
                string = self.name 
                string += " má nyní karty: "
                for i in self.hand:
                    string += i + " "
                answer = input(string + "v hodnote " + str(self.getHandValue()) + ", chce další(A/N)?: ")
                if answer == 'A':
                    return True
                if answer == 'N':
                    self.check += 1
                    return False

# TEST DEAL

dealer = Dealer()
dealer.shuffle()
myHand = dealer.deal(25)
print(myHand)
myHand = dealer.deal(7)
print(myHand)
dealer.shuffle()
myHand = dealer.deal(4)
print(myHand)
# TEST GAME

newDealer = Dealer()
player1 = Player('Čeněk Člověčí', 'Cautions')
player2 = Player('Vilda Vopatrný', 'Bold')
player3 = Player('Olda Odvážný','Bold')
newDealer.addPlayer(player1)
newDealer.addPlayer(player2)
newDealer.addPlayer(player3)
#newDealer.removePlayer(player3)
newDealer.shuffle()
newDealer.startRound()
