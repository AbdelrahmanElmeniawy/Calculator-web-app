<script>
import axios from 'axios';
export default {
  data(){
    return {
      calculator: {
        previous: '',
        current: '0',
        operatorClicked: false,
        sign: '',
        p: '',
        exit: false,
        end: false
      }
    }
  },
  methods: {
    clearAll() {
      this.calculator.current = '0';
      this.calculator.previous = '';
      this.calculator.operatorClicked= false;
      this.calculator.sign= '';
      this.calculator.p= '';
      this.calculator.exit= false;
      this.calculator.end = false;
    },
    append(number) {
      if (this.calculator.exit) this.clearAll();
      if (this.calculator.end){
        this.calculator.current= '0';
        this.calculator.end = false;
      }
      if (this.calculator.operatorClicked){
        this.calculator.current = '0';
        this.calculator.operatorClicked = false;
      }
      if (this.calculator.current.length >= 10) return;
      if (number === '.' && this.calculator.current === '0') this.calculator.current = '0.';
      else if (this.calculator.current !== '0') this.calculator.current = "" + this.calculator.current + number;
      else this.calculator.current = number;
    },
    dot() {
      if (this.calculator.exit) {
        this.clearAll();
      } else {
        if (this.calculator.current.indexOf('.') === -1 || this.calculator.operatorClicked || this.calculator.end)
          this.append('.');
      }
    },
    setPrevious() {
      this.calculator.previous = this.calculator.current
      this.calculator.operatorClicked = true;
    },
    del() {
      if (this.calculator.exit) this.clearAll();
      if (this.calculator.end) return;
      if(this.calculator.current != ''){
        if (this.calculator.current.length === 1 || (this.calculator.current.length === 2 && this.calculator.current.charAt(0) === '-')){
            this.calculator.current = '0';
        }
        else {
          this.calculator.current = this.calculator.current.slice(0, -1);
        }
      }
    },
    async op1(x){
      if (this.calculator.exit) this.clearAll();
      const ans = await axios.get('http://localhost:8085/' + x, {params:{first:parseFloat(this.calculator.current),},});
      this.calculator.current = ans.data;
      if (this.calculator.current === 'Invalid Input'){
        this.calculator.previous = 'press any key';
        this.calculator.exit = true;
      }
    },
    op2(x, s){
      if (this.calculator.exit) {
        this.clearAll();
      } else {
        this.calculator.end = false;
        this.calculator.p = x;
        this.setPrevious()
        this.calculator.sign = s
      }
    },
    async equal() {
      if (this.calculator.exit || this.calculator.end || this.calculator.p === '') {
        this.clearAll();
      } else {
        const ans = await axios.get('http://localhost:8085/' + this.calculator.p, 
          {params:{first:parseFloat(this.calculator.current), second:parseFloat(this.calculator.previous),},});
        this.calculator.current = ans.data;
        if (this.calculator.current === 'Invalid Input'){
          this.calculator.previous = 'press any key';
          this.calculator.exit = true;
          return;
        }
        this.calculator.previous = '';
        this.calculator.sign = '';
        this.calculator.end = true;
      }
    }
  }
}
</script>

<template>
  <div class="container">
    <div class="calculator">
      <div class="prev">{{ calculator.previous }} {{ calculator.sign }}</div>
      <div class="display">{{ calculator.current }}</div>
      <div @click="op1('percent')" class="btn operator" aria-disabled="calculator.current === '0'">%</div>
      <div @click="op1('sqrt')" class="btn operator">sqrt</div>
      <div @click="op1('square')" class="btn operator">x^2</div>
      <div @click="op1('inverse')" class="btn operator">1/x</div>
      <div @click="clearAll" class="btn operator2 c">C</div>
      <div @click="del" class="btn operator2">del</div>
      <div @click="op2('devide', '/')" class="btn operator2">/</div>
      <div @click="append('7')" class="btn">7</div>
      <div @click="append('8')" class="btn">8</div>
      <div @click="append('9')" class="btn">9</div>
      <div @click="op2('multi', '*')" class="btn operator2">*</div>
      <div @click="append('4')" class="btn">4</div>
      <div @click="append('5')" class="btn">5</div>
      <div @click="append('6')" class="btn">6</div>
      <div @click="op2('minus', '-')" class="btn operator2">-</div>
      <div @click="append('1')" class="btn">1</div>
      <div @click="append('2')" class="btn">2</div>
      <div @click="append('3')" class="btn">3</div>
      <div @click="op2('add', '+')" class="btn operator2">+</div>
      <div @click="op1('sign')" class="btn">+/-</div>
      <div @click="append('0')" class="btn">0</div>
      <div @click="dot" class="btn">.</div>
      <div @click="equal" class="btn operator2">=</div>
    </div>
  </div>
</template>

<style scoped>
* { font-size: 25px;
margin: 0;
padding: 0;
box-sizing: border-box;
 
 }

.container{
  background-color: #008ea7;
  display: flex;
align-items: center;
justify-content: center;
height: 100vh;
width: 100vw;
}

.calculator {
  margin: 0 auto;
  width: 350px;
  height: 500px;
  text-align: center;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-auto-rows: minmax(30px, auto);
  box-shadow: 5px 10px 8px #888888;
  background-color: rgb(255, 255, 255);
  border-radius: 5px;
}

.display {
  padding: 20px;
  grid-column:  1 / 5;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  background-color: rgb(255, 255, 255);
  color: rgb(0, 0, 0);
  border-radius: 5px;
}

.prev {
  grid-column:  1 / 5;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  background-color: rgb(0, 0, 0);
  color: rgb(255, 255, 255);
  font-size: 18px;
}

.c {
  grid-column:  1 / 3;
}

.btn {
  background-color: #f2f2f2;
  border: 1px solid #333;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 3px;
}
.btn:hover { background-color: rgb(153, 161, 156); cursor: pointer; }

.operator { background-color:rgb(152, 177, 219) }
.operator:hover { background-color: rgb(28, 87, 189); cursor: pointer; }
.operator2 { background-color: rgb(152, 177, 219) }
.operator2:hover { background-color: rgb(20, 88, 206); cursor: pointer; }

</style>