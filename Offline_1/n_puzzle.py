goalBoard=[[1, 2, 3], [4, 5, 6], [7, 8, 0]]

def hammingHeuristic(k, board):
    hcost=0
    for i in range(k):
        for j in range(k):
            if (board[i][j]!=0) & (board[i][j]!=goalBoard[i][j]):
                hcost+=1
    return hcost

def manhattanHeuristic(k, board):
    hcost=0
    for i in range(k):
        for j in range(k):
            hcost+=abs(int(board[i][j]/k)-i)+abs((board[i][j]%k)-j)
    return hcost

def checkInversion(k, board):
    for i in range(k):
        for j in range(k):
            
    # if (k%2==1):
        

def main():
    k=input()
    k=int(k)
    board=[]
    
    for i in range(k):
        row=[]
        for j in range(k):
            num=input()
            if num=='*':
                num=0
            row.append(int(num))
        board.append(row)
    for i in range(k):
        for j in range(k):
            print(board[i][j],end=" ")
        print()

            

    # print("Hamming cost: ")
    # print(hammingHeuristic(k, board))
    print("Manhattan cost: ")
    print(manhattanHeuristic(k, board))


if __name__=="__main__":
    main()