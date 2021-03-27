class LRUCache {
    private Map<Integer, Node> map;
    Node head = null;
    Node tail = null;
    int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<Integer, Node>();
    }
    
    public int get(int key) {
        if(map.containsKey(key)){
            Node target = map.get(key);
            int value = target.value;
            target.update();
            return value;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node target = map.get(key);
            target.value = value;
            target.update();
        }else{
            if (capacity == 0) return;
            if(map.size() == capacity){
                map.remove(head.key);
                head.removeFromHead();
            }
            Node newNode = new Node(key, value);
            newNode.append();
            map.put(key, newNode);
        }
    }
    
    class Node{
    int key;
    int value;
    Node prev = null;
    Node next = null;
    
    public Node(int key, int value){
        this.key = key;
        this.value = value;
    }
    
    private  void removeFromHead(){
        if(tail == this){
            head = null;
            tail = null;
        }else{
                head = this.next;
                head.prev = null;
            }
    }
    
    private void update(){
        if(tail == this)return;
        else{
            if(this != head){
                this.prev.next = this.next;
            }else{
                head = this.next;
            }
            this.next.prev = this.prev;
            this.append();
        }
    }
    
    private void append(){
        if(tail == null){
            head = this;
            tail = this;
        }else{
            this.next = null;
            this.prev = tail;
            tail.next = this;
            tail = this;
        }
    }
}
}



/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
